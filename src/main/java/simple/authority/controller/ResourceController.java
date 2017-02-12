package simple.authority.controller;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import simple.authority.entity.Resource;
import simple.authority.repository.ResourceRepository;
import simple.authority.swagger.doc_hidden.AccessHiddenManager;

@RequestMapping("/resource")
@Api("resource相关api")
@RestController
@Log
public class ResourceController //extends AbstractUIController<ResourceRepository>
{

    @Autowired
    private ResourceRepository resourceRepository;

    /*@Override
    public ResourceRepository getRepository() {
        return resourceRepository;
    }*/

    @ApiOperation(value = "获取资源列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Resource> getUserList() {
        return resourceRepository.findAll();
    }

    @ApiOperation(value = "添加资源", notes = "根据Resource对象创建资源", extensions = {
            @Extension(properties = @ExtensionProperty(name = AccessHiddenManager.HIDDEN, value = "roles"))
    })
    @ApiImplicitParam(name = "resource", value = "资源详细实体resource", required = true, dataType = "Resource")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(@RequestBody Resource resource) {

        log.info(resource.toString());
        try {
            resourceRepository.save(resource);
            return "success";
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalArgumentException("提交了重复的编码，资源创建失败。重复编码：" + resource.getCode(), exception);
        }
    }
}
