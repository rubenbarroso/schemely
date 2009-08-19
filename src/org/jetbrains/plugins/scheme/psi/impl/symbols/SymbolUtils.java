package org.jetbrains.plugins.scheme.psi.impl.symbols;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.plugins.scheme.psi.api.SchemeList;
import org.jetbrains.plugins.scheme.psi.api.symbols.SchemeIdentifier;

import javax.swing.*;

/**
 * @author ilyas
 */
public class SymbolUtils
{

  public static Icon getIcon(SchemeIdentifier symbol, int flags)
  {
    PsiElement parent = PsiTreeUtil.getParentOfType(symbol, SchemeList.class);
    if (parent instanceof SchemeList)
    {
      SchemeList list = (SchemeList) parent;

      // Functions and defs
      if (symbol == list.getSecondNonLeafElement())
      {
        PsiElement fst = list.getFirstNonLeafElement();
        if (fst instanceof SchemeIdentifier)
        {
          SchemeIdentifier lstSym = (SchemeIdentifier) fst;
          String nameString = lstSym.getNameString();

//          if (FN.equals(nameString))
//          {
//            return SchemeIcons.FUNCTION;
//          }
//          if (DEFN.equals(nameString))
//          {
//            return SchemeIcons.FUNCTION;
//          }
        }
      }


    }

    return null;
  }
}
