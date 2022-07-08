/*
  Source code is in UTF-8 encoding. The following symbols may appear, among others:
  α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω « • ¦ » ∀ ∃ ∷ … → ← ﬁ ﬂ ƒ
  If you can't read this, you're out of luck. This code was generated with the frege compiler version 3.25.84
  from
  /Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/lsp4j/PositionLSP4J.fr
  Do not edit this file! Instead, edit the source file and recompile.
*/

package ch.fhnw.thga.fregelanguageserver.lsp4j;

import frege.run8.Func;
import frege.run8.Lazy;
import frege.run8.Thunk;
import frege.run.Kind;
import frege.run.RunTM;
import frege.runtime.Meta;
import frege.runtime.Phantom.RealWorld;
import ch.fhnw.thga.fregelanguageserver.types.Position;
import frege.Prelude;
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
  source="/Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/lsp4j/PositionLSP4J.fr",
  time=1657103704716L, jmajor=11, jminor=-1,
  imps={
    "ch.fhnw.thga.fregelanguageserver.types.Position", "frege.Prelude", "frege.prelude.PreludeArrays",
    "frege.prelude.PreludeBase", "frege.prelude.PreludeDecimal", "frege.prelude.PreludeIO", "frege.prelude.PreludeList",
    "frege.prelude.PreludeMonad", "frege.prelude.PreludeText", "frege.java.util.Regex"
  },
  nmss={
    "Position", "Prelude", "PreludeArrays", "PreludeBase", "PreludeDecimal", "PreludeIO", "PreludeList",
    "PreludeMonad", "PreludeText", "Regexp"
  },
  symas={}, symcs={}, symis={},
  symts={
    @Meta.SymT(
      offset=139,
      name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.lsp4j.PositionLSP4J", base="PositionLSP"), typ=0,
      kind=3, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=470,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.lsp4j.PositionLSP4J", base="PositionLSP",
            member="toPosition"
          ),
          stri="s(u)", sig=1, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=214,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.lsp4j.PositionLSP4J", base="PositionLSP",
            member="new"
          ),
          stri="s(ss)", sig=3, nativ="new", pur=true, depth=2, rkind=9
        ),
        @Meta.SymV(
          offset=363,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.lsp4j.PositionLSP4J", base="PositionLSP",
            member="fromPosition"
          ),
          stri="s(s)", sig=5, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=323,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.lsp4j.PositionLSP4J", base="PositionLSP",
            member="getCharacter"
          ),
          stri="s(s)", sig=6, nativ="getCharacter", pur=true, depth=1, rkind=9
        ),
        @Meta.SymV(
          offset=272,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.lsp4j.PositionLSP4J", base="PositionLSP",
            member="getLine"
          ),
          stri="s(s)", sig=6, nativ="getLine", pur=true, depth=1, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.Position"
    )
  },
  symvs={}, symls={},
  taus={
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.lsp4j.PositionLSP4J", base="PositionLSP")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.Position", base="Position")}
    ),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Int")}),
    @Meta.Tau(kind=9)
  },
  rhos={
    @Meta.Rho(rhofun=false, rhotau=0), @Meta.Rho(rhofun=false, rhotau=1), @Meta.Rho(sigma=0, rhotau=1),
    @Meta.Rho(rhofun=false, rhotau=2), @Meta.Rho(sigma=2, rhotau=0), @Meta.Rho(sigma=2, rhotau=4),
    @Meta.Rho(sigma=4, rhotau=0), @Meta.Rho(sigma=0, rhotau=3)
  },
  sigmas={
    @Meta.Sigma(rho=0), @Meta.Sigma(rho=2), @Meta.Sigma(rho=3), @Meta.Sigma(rho=5), @Meta.Sigma(rho=1),
    @Meta.Sigma(rho=6), @Meta.Sigma(rho=7)
  },
  exprs={@Meta.Expr()}
)
final public class PositionLSP4J  {
  



final public static class TPositionLSP  {
  final public static Position.TPosition toPosition(final Lazy<org.eclipse.lsp4j.Position> arg$1) {
    return Position.TPosition.mk(
              Thunk.<Integer>shared((Lazy<Integer>)(() -> arg$1.call().getLine() + 1)),
              Thunk.<Integer>shared((Lazy<Integer>)(() -> arg$1.call().getCharacter() + 1))
            );
  }
  final public static org.eclipse.lsp4j.Position fromPosition(final Position.TPosition arg$1) {
    return new org.eclipse.lsp4j.Position(Position.TPosition.line(arg$1) - 1, Position.TPosition.character(arg$1) - 1);
  }
}


}
