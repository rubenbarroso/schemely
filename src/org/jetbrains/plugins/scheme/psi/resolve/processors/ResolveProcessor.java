package org.jetbrains.plugins.scheme.psi.resolve.processors;

import com.intellij.psi.scope.ElementClassHint;
import com.intellij.psi.scope.NameHint;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.containers.HashSet;
import org.jetbrains.plugins.scheme.psi.resolve.SchemeResolveResult;

/**
 * @author ilyas
 */
public abstract class ResolveProcessor implements PsiScopeProcessor, NameHint, ElementClassHint
{
  protected HashSet<SchemeResolveResult> myCandidates = new HashSet<SchemeResolveResult>();
  protected final String myName;

  public ResolveProcessor(String myName)
  {
    this.myName = myName;
  }

  public SchemeResolveResult[] getCandidates()
  {
    return myCandidates.toArray(new SchemeResolveResult[myCandidates.size()]);
  }

  public <T> T getHint(Class<T> hintClass)
  {
    if (NameHint.class == hintClass && myName != null)
    {
      return (T) this;
    }
    else if (ElementClassHint.class == hintClass)
    {
      return (T) this;
    }

    return null;
  }

  public void handleEvent(Event event, Object o)
  {
  }
}
