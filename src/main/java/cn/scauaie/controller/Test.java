package cn.scauaie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-05 13:03
 */
@RestController
@RequestMapping("/form")
public class Test {

    @RequestMapping(value = "/user.do" , method = RequestMethod.POST)
    public String create(String code) {
        System.out.println("code = [" + code + "]");
        return "code = [" + code + "]";
    }


}
