package org.jetbrains.plugins.scheme.psi.util;

import com.intellij.openapi.util.Trinity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.util.containers.HashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.scheme.psi.SchemePsiElement;
import org.jetbrains.plugins.scheme.psi.api.SchemeList;
import org.jetbrains.plugins.scheme.psi.api.symbols.SchemeIdentifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * @author ilyas
 */
public class SchemePsiUtil
{
  public static final String GEN_CLASS = ":gen-class";
  public static final String EXTENDS = ":extends";
  public static final String IMPLEMENTS = ":implements";
  public static final String JAVA_LANG = "java.lang";
  public static final String SCHEME_LANG = "scheme.lang";

  public static final Set<String> DEFINITION_FROM_NAMES = new HashSet<String>();

  static
  {
    DEFINITION_FROM_NAMES.addAll(Arrays.asList("fn"));
  }

  @Nullable
  public static SchemeList findFormByName(SchemePsiElement container, @NotNull String name)
  {
    for (PsiElement element : container.getChildren())
    {
      if (element instanceof SchemeList)
      {
        SchemeList list = (SchemeList) element;
        SchemeIdentifier first = list.getFirstIdentifier();
        if (first != null && name.equals(first.getNameString()))
        {
          return list;
        }
      }
    }
    return null;
  }

  @Nullable
  public static SchemeList findFormByNameSet(SchemePsiElement container, @NotNull Set<String> names)
  {
    for (PsiElement element : container.getChildren())
    {
      if (element instanceof SchemeList)
      {
        SchemeList list = (SchemeList) element;
        SchemeIdentifier first = list.getFirstIdentifier();
        if (first != null && names.contains(first.getNameString()))
        {
          return list;
        }
      }
    }
    return null;
  }

  public static <T> T findNextSiblingByClass(PsiElement element, Class<T> aClass)
  {
    PsiElement next = element.getNextSibling();
    while (next != null && !aClass.isInstance(next))
    {
      next = next.getNextSibling();
    }
    return (T) next;
  }

//  public static ClKeyImpl findNamespaceKeyByName(SchemeList ns, String keyName)
//  {
//    final SchemeList list = ns.findFirstChildByClass(SchemeList.class);
//    if (list == null)
//    {
//      return null;
//    }
//    for (PsiElement element : list.getChildren())
//    {
//      if (element instanceof ClKeyImpl)
//      {
//        ClKeyImpl key = (ClKeyImpl) element;
//        if (keyName.equals(key.getText()))
//        {
//          return key;
//        }
//      }
//    }
//    return null;
//  }

  @Nullable
  public static PsiElement getNextNonWhiteSpace(PsiElement element)
  {
    PsiElement next = element.getNextSibling();
    while (next != null && (next instanceof PsiWhiteSpace))
    {
      next = next.getNextSibling();
    }
    return next;
  }

  @NotNull
  public static Trinity<PsiElement, PsiElement, PsiElement> findCommonParentAndLastChildren(
    @NotNull PsiElement element1, @NotNull PsiElement element2)
  {
    if (element1 == element2)
    {
      return new Trinity<PsiElement, PsiElement, PsiElement>(element1, element1, element1);
    }
    PsiFile containingFile = element1.getContainingFile();
    PsiElement topLevel = containingFile == element2.getContainingFile() ? containingFile : null;

    ArrayList<PsiElement> parents1 = getParents(element1, topLevel);
    ArrayList<PsiElement> parents2 = getParents(element2, topLevel);
    int size = Math.min(parents1.size(), parents2.size());
    PsiElement parent = topLevel;
    for (int i = 1; i <= size; i++)
    {
      PsiElement parent1 = parents1.get(parents1.size() - i);
      PsiElement parent2 = parents2.get(parents2.size() - i);

      if (!parent1.equals(parent2))
      {
        return new Trinity<PsiElement, PsiElement, PsiElement>(parent, parent1, parent2);
      }
      parent = parent1;
    }
    return new Trinity<PsiElement, PsiElement, PsiElement>(parent, parent, parent);
  }

  public static boolean lessThan(PsiElement elem1, PsiElement elem2)
  {
    if (elem1.getParent() != elem2.getParent() || elem1 == elem2)
    {
      return false;
    }
    PsiElement next = elem1;
    while (next != null && next != elem2)
    {
      next = next.getNextSibling();
    }
    return next != null;
  }

  @NotNull
  public static ArrayList<PsiElement> getParents(@NotNull PsiElement element, @Nullable PsiElement topLevel)
  {
    ArrayList<PsiElement> parents = new ArrayList<PsiElement>();
    PsiElement parent = element;
    while (parent != topLevel && parent != null)
    {
      parents.add(parent);
      parent = parent.getParent();
    }
    return parents;
  }

  private static boolean isParameterSymbol(SchemeIdentifier symbol)
  {
    //todo implement me!
    return false;
  }
}
