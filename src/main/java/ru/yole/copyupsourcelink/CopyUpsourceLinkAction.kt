package ru.yole.copyupsourcelink

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VfsUtilCore
import java.awt.datatransfer.StringSelection

class CopyUpsourceLinkAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val vFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val baseDir = project.baseDir.findChild("community") ?: project.baseDir
        val relativePath = VfsUtilCore.getRelativePath(vFile, baseDir)
        CopyPasteManager.getInstance().setContents(StringSelection("upsource:///$relativePath"))
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val vFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = vFile != null && project != null &&
                ProjectFileIndex.getInstance(project).isInSource(vFile)
    }
}
