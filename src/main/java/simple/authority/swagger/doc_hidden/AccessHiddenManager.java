package simple.authority.swagger.doc_hidden;

import com.google.common.collect.ImmutableList;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import springfox.documentation.schema.Model;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ApiListingBuilderPlugin;
import springfox.documentation.spi.service.contexts.ApiListingContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Log
public class AccessHiddenManager implements ApiListingBuilderPlugin {

    /**
     * 用于保存需要隐藏的属性与相关api操作文档的对应关系
     */
    public static final Map<String, List<String>> hiddenMapByApi = new HashMap<>();

    public static final String HIDDEN = "hidden";

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

    @Override
    public void apply(ApiListingContext apiListingContext) {
        List<ApiDescription> apis = apiListingContext.apiListingBuilder().build().getApis();

        // 循环操作的每个文档，对需要隐藏的属性进行记录。
        for (ApiDescription ad : apis) {
            for (Operation op : ad.getOperations()) {
                List<VendorExtension> vendorExtensions = op.getVendorExtensions();
                List<String> exs = new ArrayList<>();
                for (VendorExtension<ImmutableList<StringVendorExtension>> ex : vendorExtensions) {
                    for (StringVendorExtension sve : ex.getValue()) {
                        if (HIDDEN.equals(sve.getName())) {
                            exs.add(sve.getValue());
                        }
                    }
                }
                if (!exs.isEmpty()) {
                    // 因为tag标签会对操作分组，所以这里也需要判断一下，来存放关系
                    Set<String> tags = op.getTags();
                    if (op.getTags().isEmpty()) {
                        hiddenMapByApi.put(ad.getDescription() + "_" + op.getUniqueId(), exs);
                    } else {
                        for (String tag : tags) {
                            hiddenMapByApi.put(tag + "_" + op.getUniqueId(), exs);
                        }
                    }
                }
            }
        }
    }
}
