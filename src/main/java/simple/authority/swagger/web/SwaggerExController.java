// ======================================================================
//
//      Copyright (C) 北京国双科技有限公司
//                    http://www.gridsum.com
//
//      保密性声明：此文件属北京国双科技有限公司所有，仅限拥有由国双科技
//      授予了相应权限的人所查看和所修改。如果你没有被国双科技授予相应的
//      权限而得到此文件，请删除此文件。未得国双科技同意，不得查看、修改、
//      散播此文件。
//
//
// ======================================================================

package simple.authority.swagger.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import simple.authority.swagger.doc_hidden.AccessHiddenManager;

import java.util.List;
import java.util.Map;

@RequestMapping("/swaggerEx")
@RestController
public class SwaggerExController {

    @RequestMapping(value = "hiddenMaps", method = RequestMethod.GET)
    public Map<String, List<String>> hiddenMaps() {
        return AccessHiddenManager.hiddenMapByApi;
    }
}
