/*
  Source code is in UTF-8 encoding. The following symbols may appear, among others:
  α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω « • ¦ » ∀ ∃ ∷ … → ← ﬁ ﬂ ƒ
  If you can't read this, you're out of luck. This code was generated with the frege compiler version 3.25.84
  from
  /Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/types/lsp/RangeLSP.fr
  Do not edit this file! Instead, edit the source file and recompile.
*/

package ch.fhnw.thga.fregelanguageserver.types.lsp;

import frege.run8.Func;
import frege.run8.Lazy;
import frege.run8.Thunk;
import frege.run.Kind;
import frege.run.RunTM;
import frege.runtime.Meta;
import frege.runtime.Phantom.RealWorld;
import ch.fhnw.thga.fregelanguageserver.types.Position;
import ch.fhnw.thga.fregelanguageserver.types.Range;
import ch.fhnw.thga.fregelanguageserver.types.lsp.PositionLSP;
import frege.Prelude;
import frege.compiler.enums.TokenID;
import frege.compiler.types.Tokens;
import frege.control.Category;
import frege.control.Semigroupoid;
import frege.java.IO;
import frege.java.Lang;
import frege.java.Util;
import frege.java.util.Regex;
import frege.prelude.Maybe;
import frege.prelude.PreludeArrays;
import frege.prelude.PreludeBase;
import frege.prelude.PreludeDecimal;
import frege.prelude.PreludeIO;
import frege.prelude.PreludeList;
import frege.prelude.PreludeMonad;
import frege.prelude.PreludeText;

@SuppressWarnings("unused")
@Meta.FregePackage(
  source="/Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/types/lsp/RangeLSP.fr",
  time=1661518378882L, jmajor=11, jminor=-1,
  imps={
    "ch.fhnw.thga.fregelanguageserver.types.lsp.PositionLSP", "frege.Prelude", "frege.prelude.PreludeArrays",
    "frege.prelude.PreludeBase", "frege.prelude.PreludeDecimal", "frege.prelude.PreludeIO", "frege.prelude.PreludeList",
    "frege.prelude.PreludeMonad", "frege.prelude.PreludeText", "ch.fhnw.thga.fregelanguageserver.types.Range",
    "frege.java.util.Regex"
  },
  nmss={
    "PositionLSP", "Prelude", "PreludeArrays", "PreludeBase", "PreludeDecimal", "PreludeIO",
    "PreludeList", "PreludeMonad", "PreludeText", "Range", "Regexp"
  },
  symas={}, symcs={}, symis={},
  symts={
    @Meta.SymT(
      offset=206, name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.lsp.RangeLSP", base="RangeLSP"),
      typ=0, kind=3, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=331,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.lsp.RangeLSP", base="RangeLSP",
            member="fromRange"
          ),
          stri="s(s(ss))", sig=2, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=275,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.lsp.RangeLSP", base="RangeLSP",
            member="new"
          ),
          stri="s(ss)", sig=4, nativ="new", pur=true, depth=2, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.Range"
    )
  },
  symvs={}, symls={},
  taus={
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.lsp.RangeLSP", base="RangeLSP")}
    ),
    @Meta.Tau(
      kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.lsp.PositionLSP", base="PositionLSP")}
    ),
    @Meta.Tau(kind=9)
  },
  rhos={
    @Meta.Rho(rhofun=false, rhotau=0), @Meta.Rho(rhofun=false, rhotau=1), @Meta.Rho(sigma=1, rhotau=0),
    @Meta.Rho(rhofun=false, rhotau=2), @Meta.Rho(sigma=3, rhotau=0), @Meta.Rho(sigma=3, rhotau=4)
  },
  sigmas={@Meta.Sigma(rho=0), @Meta.Sigma(rho=1), @Meta.Sigma(rho=2), @Meta.Sigma(rho=3), @Meta.Sigma(rho=5)},
  exprs={@Meta.Expr()}
)
final public class RangeLSP  {
  



final public static class TRangeLSP  {
  final public static org.eclipse.lsp4j.Range fromRange(final Range.TRange arg$1) {
    final Position.TPosition end$8081 = arg$1.mem$end.call();
    final Position.TPosition start$8080 = arg$1.mem$start.call();
    return new org.eclipse.lsp4j.Range(
          PositionLSP.TPositionLSP.fromPosition(start$8080), PositionLSP.TPositionLSP.fromPosition(end$8081)
        );
  }
}


}
