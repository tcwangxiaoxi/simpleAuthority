package simple.authority.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import simple.authority.entity.Resource;
import simple.authority.entity.Role;
import simple.authority.repository.ResourceRepository;
import simple.authority.repository.RoleRepository;

@RequestMapping("/role")
@Api("role相关api")
@RestController
@Log
public class RoleController //extends AbstractUIController<ResourceRepository>
{

    @Autowired
    private RoleRepository roleRepository;

    /*@Override
    public ResourceRepository getRepository() {
        return resourceRepository;
    }*/

    @ApiOperation(value = "获取角色列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Role> getUserList() {
        return roleRepository.findAll();
    }

    @ApiOperation(value = "创建角色", notes = "根据Role对象创建角色")
    @ApiImplicitParam(name = "role", value = "角色对象", required = true, dataType = "Role")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(@RequestBody Role role) {
        try {
            roleRepository.save(role);
            return "success";
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalArgumentException("提交了重复的编码，角色创建失败。重复编码：" + role.getCode(), exception);
        }
    }
}
