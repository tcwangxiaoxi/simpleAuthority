package simple.authority.controller;

import io.swagger.annotations.*;
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
import simple.authority.swagger.doc_hidden.AccessHiddenManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("/role")
@Api("role相关api")
@RestController
@Log
public class RoleController //extends AbstractUIController<ResourceRepository>
{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    /*@Override
    public ResourceRepository getRepository() {
        return resourceRepository;
    }*/

    @ApiOperation(value = "获取角色列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Role> getUserList() {
        return roleRepository.findAll();
    }

    @ApiOperation(value = "创建角色", notes = "根据Role对象创建角色", extensions = {
            @Extension(properties = @ExtensionProperty(name = AccessHiddenManager.HIDDEN, value = "users,isAvailable")),
            @Extension(properties = @ExtensionProperty(name = AccessHiddenManager.ONLY_SHOW, value = "resources.code"))
    })
    @ApiImplicitParam(name = "role", value = "角色对象", required = true, dataType = "Role")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(@RequestBody Role role) {
        try {
            List<Resource> resources = new ArrayList<>();
            for (Resource resource : role.getResources()) {
                resources.add(resourceRepository.findByCode(resource.getCode()));
            }
            role.setResources(resources);
            roleRepository.save(role);
            return "success";
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalArgumentException("提交了重复的编码，角色创建失败。重复编码：" + role.getCode(), exception);
        }
    }
}
