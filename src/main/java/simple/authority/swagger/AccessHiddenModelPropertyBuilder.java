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

package simple.authority.swagger;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import springfox.documentation.schema.Model;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ApiListingBuilderPlugin;
import springfox.documentation.spi.service.contexts.ApiListingContext;

import java.util.Map;

@Log
public class AccessHiddenModelPropertyBuilder implements ApiListingBuilderPlugin {

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

    @Override
    public void apply(ApiListingContext apiListingContext) {
        Map<String, Model> models = apiListingContext.apiListingBuilder().build().getModels();

        // 循环操作的每个参数，对需要做改变的model的属性进行修改，然后重新设置回models
        // for(
            // 循环参数的每个属性
            //models.get("").getProperties();
        // )

        //apiListingContext.apiListingBuilder().models(models);
    }
}
