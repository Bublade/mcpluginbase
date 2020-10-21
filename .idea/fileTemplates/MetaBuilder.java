#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

#parse("File Header.java")
import com.bubladecoding.mcpluginbase.item.ItemBuilder;

import org.bukkit.inventory.meta.${META_CLASS};

/**
 * MetaBuilder class for editing {@link ${META_CLASS}}.
 */
public class ${NAME} extends MetaBuilder<${META_CLASS}, ${NAME}> {

    public ${NAME}(ItemBuilder itemBuilder) {
        super(itemBuilder, ${META_CLASS}.class, ${NAME}.class);
    }
}