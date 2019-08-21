package cn.scauaie.service.impl;

import cn.scauaie.dao.AuthCodeMapper;
import cn.scauaie.model.ao.AuthCodeAO;
import cn.scauaie.model.dao.AuthCodeDO;
import cn.scauaie.service.AuthCodeService;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-21 16:18
 */
@Service("authCodeService")
public class AuthCodeServiceImpl implements AuthCodeService {

    @Autowired
    private AuthCodeMapper authCodeMapper;

    @Autowired
    private Mapper mapper;

    /**
     * 生成认证码
     *
     * @param dep 部门
     * @param count 数量
     * @return List<AuthCodeAO>
     */
    @Override
    public List<AuthCodeAO> createAndSaveAuthCodes(String dep, Integer count) {
        List<AuthCodeDO> authCodeList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            authCodeList.add(new AuthCodeDO(UUID.randomUUID().toString(), dep));
        }
        authCodeMapper.insertAuthCodes(authCodeList);
        List<AuthCodeAO> authCodeAOList = new ArrayList<>();
        mapper.map(authCodeList, authCodeAOList);
        return authCodeAOList;
    }

}
