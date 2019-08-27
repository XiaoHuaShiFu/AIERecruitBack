package cn.scauaie.timer.task;

import cn.scauaie.model.ao.EvaluationAO;
import cn.scauaie.model.ao.FormAO;
import cn.scauaie.model.ao.InterviewerAO;
import cn.scauaie.model.ao.ResultAO;
import cn.scauaie.result.Result;
import cn.scauaie.service.EvaluationService;
import cn.scauaie.service.FormService;
import cn.scauaie.service.InterviewerService;
import cn.scauaie.service.ResultService;
import cn.scauaie.util.PropertiesUtils;
import cn.scauaie.util.email.Email;
import cn.scauaie.util.email.EmailUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述: 自动备份招新信息任务
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-27 9:16
 */
@Component
public class BackupTask extends TimerTask {

    private static final Logger logger = LoggerFactory.getLogger(BackupTask.class);

    @Autowired
    private FormService formService;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private InterviewerService interviewerService;

    /**
     * 当前系统换行符
     */
    private static final String FILE_CONTENT_SPLIT_MARK = System.getProperty("line.separator");

    /**
     * 暂存文件的文件夹
     */
    private static final String DIRECTORY_PREFIX = "D:\\buf_file\\";

    /**
     * 日期格式器
     */
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

    /**
     * Form文件各个字段的名字
     */
    private static final String FORM_COLUMN_NAMES =
            "编号,姓名,性别,学院,专业,手机,头像,第一志愿部门,第二志愿部门,个人简介";

    /**
     * Result文件各个字段的名字
     */
    private static final String RESULT_COLUMN_NAMES = "编号,报名表编号,结果";

    /**
     * Interviewer文件各个字段的名字
     */
    private static final String INTERVIEWER_COLUMN_NAMES = "编号,姓名,部门";

    /**
     * Evaluation文件各个字段的名字
     */
    private static final String EVALUATION_COLUMN_NAMES = "编号,面试官编号,报名表编号,评价,是否通过";

    /**
     * 主机
     */
    private static final String HOST =
            PropertiesUtils.getProperty("email.host", "email.properties");

    /**
     * 邮箱
     */
    private static final String USERNAME =
            PropertiesUtils.getProperty("email.username", "email.properties");

    /**
     * 密码
     */
    private static final String PASSWORD =
            PropertiesUtils.getProperty("email.password", "email.properties");

    /**
     * 发送方
     */
    private static final String FROM =
            PropertiesUtils.getProperty("email.from", "email.properties");

    /**
     * 接收方
     */
    private static final List<String> TO = Arrays.asList(Objects.requireNonNull(
            PropertiesUtils.getProperty("email.toList", "email.properties")).split(","));

    /**
     * 备份主流程
     */
    @Override
    public void run() {
        Email mb = new Email();
        mb.setHost(HOST);
        mb.setUsername(USERNAME);
        mb.setPassword(PASSWORD);
        mb.setFrom(FROM);
        mb.setTo(TO);
        mb.setSubject("招新资料备份" + format.format(new Date()));
        mb.setContent("本邮件中包含四个附件，请检查！");
        ArrayList<String> fileNameList = new ArrayList<>(4);
        fileNameList.add(backupForm());
        fileNameList.add(backupEvaluation());
        fileNameList.add(backupInterviewer());
        fileNameList.add(backupResult());
        mb.setFile(fileNameList);
        if (!EmailUtils.sendEmail(mb)) {
            logger.error("Send email fail.");
        }
    }

    /**
     * 备份报名表
     * @return 文件名
     */
    private String backupForm() {
        Result<List<FormAO>> result = formService.listForms(1, 2000);
        if (!result.isSuccess()) {
            logger.error(result.getMessage());
            return StringUtils.EMPTY;
        }
        return createAndWriteFile("form", result.getData(), form -> form.getId() + ","
                + form.getName() + "," + form.getGender() + "," + form.getCollege() + "," + form.getMajor() + ","
                + form.getPhone() + "," + form.getAvatar() + "," + form.getFirstDep() + "," + form.getSecondDep() + ","
                + form.getIntroduction(), FORM_COLUMN_NAMES);
    }

    /**
     * 备份面试结果
     * @return 文件名
     */
    private String backupResult() {
        Result<List<ResultAO>> result = resultService.listResults(1, 2000);
        if (!result.isSuccess()) {
            logger.error(result.getMessage());
            return StringUtils.EMPTY;
        }
        return createAndWriteFile("result", result.getData(),
                r -> r.getId() + "," + r.getFid() + "," + r.getResult(),RESULT_COLUMN_NAMES);
    }

    /**
     * 备份面试官
     * @return 文件名
     */
    private String backupInterviewer() {
        Result<List<InterviewerAO>> result = interviewerService.listInterviewers(1, 2000);
        if (!result.isSuccess()) {
            logger.error(result.getMessage());
            return StringUtils.EMPTY;
        }
        return createAndWriteFile("interviewer", result.getData(), interviewer -> interviewer.getId() + ","
                + interviewer.getName() + "," + interviewer.getDep(), INTERVIEWER_COLUMN_NAMES);
    }

    /**
     * 备份评价表
     * @return 文件名
     */
    private String backupEvaluation() {
        Result<List<EvaluationAO>> result = evaluationService.listEvaluations(1, 2000);
        if (!result.isSuccess()) {
            logger.error(result.getMessage());
            return StringUtils.EMPTY;
        }
        return createAndWriteFile("evaluation", result.getData(),
                evaluation -> evaluation.getId() + "," + evaluation.getIid() + "," + evaluation.getFid() + ","
                        + evaluation.getEvaluation() + "," + evaluation.getPass(), EVALUATION_COLUMN_NAMES);
    }

    /**
     * 创建并写入文件
     *
     * @param fileName 文件名
     * @param list 内容
     * @param converter 转换器
     * @return fileName 文件名
     */
    private <T> String createAndWriteFile(String fileName, List<T> list, Converter<T, String> converter, String columnNames) {
        fileName = DIRECTORY_PREFIX + fileName + format.format(new Date()) + ".csv";

        File file = new File(fileName);
        try {
            if (!file.createNewFile()) {
                logger.error("Create file: {} fail", fileName);
            }
        } catch (IOException e) {
            logger.error("Create file: {} fail", fileName);
        }
        try (PrintWriter printWriter = new PrintWriter(file)) {
            StringBuilder stringBuilder = new StringBuilder().append(columnNames).append(FILE_CONTENT_SPLIT_MARK);
            list.forEach(formAO -> stringBuilder.append(converter.convert(formAO)).append(FILE_CONTENT_SPLIT_MARK));

            printWriter.print(stringBuilder);
            printWriter.flush();
        } catch (FileNotFoundException e) {
            logger.error("Write into file: {} fail.", fileName);
        }
        return fileName;
    }

}
