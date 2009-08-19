package org.jetbrains.plugins.scheme.utils;

import com.intellij.facet.FacetManager;
import com.intellij.facet.FacetTypeId;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.impl.libraries.LibraryEx;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.scheme.config.SchemeConfigUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ilyas
 */
public class LibrariesUtil
{
  public static Library[] getLibrariesByCondition(final Module module, final Condition<Library> condition)
  {
    if (module == null)
    {
      return new Library[0];
    }
    final ArrayList<Library> libraries = new ArrayList<Library>();
    ApplicationManager.getApplication().runReadAction(new Runnable()
    {
      public void run()
      {
        ModuleRootManager manager = ModuleRootManager.getInstance(module);
        ModifiableRootModel model = manager.getModifiableModel();
        for (OrderEntry entry : model.getOrderEntries())
        {
          if (entry instanceof LibraryOrderEntry)
          {
            LibraryOrderEntry libEntry = (LibraryOrderEntry) entry;
            Library library = libEntry.getLibrary();
            if (condition.value(library))
            {
              libraries.add(library);
            }
          }
        }
        model.dispose();
      }
    });
    return libraries.toArray(new Library[libraries.size()]);
  }

  public static Library[] getGlobalLibraries(Condition<Library> condition)
  {
    LibraryTable table = LibraryTablesRegistrar.getInstance().getLibraryTable();
    List<Library> libs = ContainerUtil.findAll(table.getLibraries(), condition);
    return libs.toArray(new Library[libs.size()]);
  }

}
