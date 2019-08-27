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
import cn.scauaie.util.email.Email;
import cn.scauaie.util.email.EmailUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

//994892083@qq.com
//827032783@qq.com
//859703569@qq.com

/**
 * 描述:
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
     * FORM文件各个字段的名字
     */
    private static final String FORM_COLUMN_NAMES =
            "编号,姓名,性别,学院,专业,手机,头像,第一志愿部门,第二志愿部门,个人简介";

    /**
     * 日期格式器
     */
    private static final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

    // TODO: 2019/8/27 进一步封装
    // TODO: 2019/8/27 多个接收者
    @Override
    public void run() {
        //备份报名表
        Result<List<FormAO>> formResult = formService.listForms(1, 2000);
        if (!formResult.isSuccess()) {
            return;
        }
        String formFileName = createAndWriteFile("form", formResult.getData(), form -> form.getId() + ","
                + form.getName() + "," + form.getGender() + "," + form.getCollege() + "," + form.getMajor() + ","
                + form.getPhone() + "," + form.getAvatar() + "," + form.getFirstDep() + "," + form.getSecondDep() + ","
                + form.getIntroduction());

        //备份评价表
        Result<List<EvaluationAO>> evaluationResult = evaluationService.listEvaluations(1, 2000);
        if (!evaluationResult.isSuccess()) {
            return;
        }
        String evaluationFileName = createAndWriteFile("evaluation", evaluationResult.getData(),
                evaluation -> evaluation.getIid() + "," + evaluation.getFid() + "," + evaluation.getIid() + ","
                        + evaluation.getEvaluation() + "," + evaluation.getPass());

        //备份面试结果
        Result<List<ResultAO>> resultResult = resultService.listResults(1, 2000);
        if (!resultResult.isSuccess()) {
            return;
        }
        String resultFileName = createAndWriteFile("result", resultResult.getData(),
                result -> result.getId() + "," + result.getFid() + "," + result.getResult());

        //备份面试官
        Result<List<InterviewerAO>> interviewerResult = interviewerService.listInterviewers(1, 2000);
        if (!interviewerResult.isSuccess()) {
            return;
        }
        String interviewerFileName = createAndWriteFile("interviewer", interviewerResult.getData(),
                interviewer -> interviewer.getId() + "," + interviewer.getName() + "," + interviewer.getDep());

        Email mb = new Email();
        mb.setHost("smtp.163.com");
        mb.setUsername("scauaierecruit@163.com");
        mb.setPassword("scauaie2019");
        mb.setFrom("scauaierecruit@163.com");
        mb.setTo("827032783@qq.com");
        mb.setSubject("报名表备份" + formater.format(new Date()));
        mb.setContent("本邮件中包含一个附件，请检查！");
        ArrayList<String> fileNameList = new ArrayList<>(4);
        fileNameList.add(formFileName);
        fileNameList.add(evaluationFileName);
        fileNameList.add(resultFileName);
        fileNameList.add(interviewerFileName);
        mb.setFile(fileNameList);
        EmailUtils.sendEmail(mb);
    }

    /**
     * 创建并写入文件
     *
     * @param fileName 文件名
     * @param list 内容
     * @param converter 转换器
     * @return fileName 文件名
     */
    private <T> String createAndWriteFile(String fileName, List<T> list, Converter<T, String> converter) {
        fileName = "D:\\buf_file\\" + fileName + formater.format(new Date()) + ".csv";

        File file = new File(fileName);
        try {
            if (!file.createNewFile()) {
                logger.error("Create file: {} fail", fileName);
            }
        } catch (IOException e) {
            logger.error("Create file: {} fail", fileName);
        }
        try (PrintWriter printWriter = new PrintWriter(file)) {
            StringBuilder stringBuilder = new StringBuilder().append(FORM_COLUMN_NAMES).append(FILE_CONTENT_SPLIT_MARK);
            list.forEach(formAO -> stringBuilder.append(converter.convert(formAO)).append(FILE_CONTENT_SPLIT_MARK));

            printWriter.print(stringBuilder);
            printWriter.flush();
        } catch (FileNotFoundException e) {
            logger.error("Write into file: {} fail.", fileName);
        }
        return fileName;
    }

}
