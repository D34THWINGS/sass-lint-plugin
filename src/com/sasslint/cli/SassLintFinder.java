package com.sasslint.cli;

import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import com.sasslint.config.SassLintConfigFileUtil;
import com.wix.nodejs.NodeFinder;
import com.wix.utils.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public final class SassLintFinder {
    public static final String SASS_LINT_BASE_NAME = NodeFinder.getBinName("sass-lint");

    private SassLintFinder() {
    }

    @NotNull
    public static List<File> searchForSassLintExe(File projectRoot) {
        return NodeFinder.searchAllScopesForBin(projectRoot, SASS_LINT_BASE_NAME);
    }

    /**
     * find possible sasslint rc files
     * @param projectRoot
     * @return
     */
    public static List<String> searchForConfigFiles(final File projectRoot) {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.equals(SassLintConfigFileUtil.SASS_LINT_CONFIG);
            }
        };
        List<String> files = FileUtils.recursiveVisitor(projectRoot, filter);
        return ContainerUtil.map(files, new Function<String, String>() {
            public String fun(String curFile) {
                return FileUtils.makeRelative(projectRoot, new File(curFile));
            }
        });
    }
}