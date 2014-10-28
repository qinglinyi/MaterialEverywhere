package pw.material

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.model.ApiVersionImpl
import com.android.builder.core.DefaultApiVersion
import com.android.builder.model.ApiVersion
import com.android.sdklib.AndroidVersion
import com.android.sdklib.repository.FullRevision
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import org.gradle.api.Project
import pw.material.internal.AutoConfigureExtension
import pw.material.internal.ICopy
import pw.material.internal.MaterialProjectState

public class MaterialTarget extends AutoConfigureExtension implements Serializable, ICopy<MaterialTarget> {
    @XStreamAsAttribute
    def int target

    @XStreamAsAttribute
    def int minTarget

    @XStreamAsAttribute
    def int maxTarget

    void apply(MaterialProjectState state) {
        final int target = getRealTarget(state.androidExtension, state.project)
        final int minTarget = getRealMinTarget(state.androidExtension, state.project)
        final int maxTarget = getRealMaxTarget(state.androidExtension, state.project)
        if (target > 0) {
            state.androidExtension.compileSdkVersion target
            state.androidExtension.defaultConfig.setTargetSdkVersion findTarget(state, target)
        }
        if (state.androidExtension.buildToolsRevision in [null, FullRevision.NOT_SPECIFIED]) {
            state.androidExtension.buildToolsVersion = state.sdkManager.latestBuildTool.revision as String
        }
        if (minTarget > 0) {
            state.androidExtension.defaultConfig.setMinSdkVersion findTarget(state, minTarget)
        }
        if (maxTarget > 0) {
            state.androidExtension.defaultConfig.setMaxSdkVersion maxTarget
        }
    }

    static ApiVersion findTarget(MaterialProjectState state, int api) {
        AndroidVersion version = state.sdkManager.targets.findAll { it.platform && it.version.apiLevel == api}.first().version
        return new DefaultApiVersion(version.apiLevel, version.codename)
    }

    private int getRealTarget(BaseExtension androidExtension, Project project) {
        if (target > 0) {
            return target
        }
        def basePlugin = project.plugins.findPlugin(MaterialBasePlugin)
        if (basePlugin != null) {
            def targetExtension = basePlugin.extension.findByName('target') as MaterialTarget
            if (targetExtension != null && targetExtension.target > 0) {
                return targetExtension.target
            }
        }
        return project.parent ? getRealTarget(androidExtension, project.parent) : -1
    }

    private int getRealMinTarget(BaseExtension androidExtension, Project project) {
        if (minTarget > 0) {
            return minTarget
        }
        def basePlugin = project.plugins.findPlugin(MaterialBasePlugin)
        if (basePlugin != null) {
            def targetExtension = basePlugin.extension.findByName('target') as MaterialTarget
            if (targetExtension != null && targetExtension.minTarget > 0) {
                return targetExtension.minTarget
            }
        }
        return project.parent ? getRealMinTarget(androidExtension, project.parent) : -1
    }

    private int getRealMaxTarget(BaseExtension androidExtension, Project project) {
        if (maxTarget > 0) {
            return maxTarget
        }
        def basePlugin = project.plugins.findPlugin(MaterialBasePlugin)
        if (basePlugin != null) {
            def targetExtension = basePlugin.extension.findByName('target') as MaterialTarget
            if (targetExtension != null && targetExtension.maxTarget > 0) {
                return targetExtension.maxTarget
            }
        }
        return project.parent ? getRealMaxTarget(androidExtension, project.parent) : -1
    }

    @Override
    MaterialTarget copy(MaterialProjectState state) {
        new MaterialTarget(
                target: getRealTarget(state.androidExtension, state.project),
                minTarget: getRealMinTarget(state.androidExtension, state.project),
                maxTarget: getRealMaxTarget(state.androidExtension, state.project))
    }
}
