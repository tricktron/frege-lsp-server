/*
  Source code is in UTF-8 encoding. The following symbols may appear, among others:
  α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω « • ¦ » ∀ ∃ ∷ … → ← ﬁ ﬂ ƒ
  If you can't read this, you're out of luck. This code was generated with the frege compiler version 3.25.84
  from /Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/types/Generics.fr
  Do not edit this file! Instead, edit the source file and recompile.
*/

package ch.fhnw.thga.fregelanguageserver.types;

import frege.run8.Func;
import frege.run8.Lazy;
import frege.run8.Thunk;
import frege.run.Kind;
import frege.run.RunTM;
import frege.runtime.Meta;
import frege.runtime.Phantom.RealWorld;
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
  source="/Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/types/Generics.fr",
  time=1661005251303L, jmajor=11, jminor=-1,
  imps={
    "frege.Prelude", "frege.prelude.PreludeArrays", "frege.prelude.PreludeBase", "frege.prelude.PreludeDecimal",
    "frege.prelude.PreludeIO", "frege.prelude.PreludeList", "frege.prelude.PreludeMonad", "frege.prelude.PreludeText",
    "frege.java.util.Regex"
  },
  nmss={
    "Prelude", "PreludeArrays", "PreludeBase", "PreludeDecimal", "PreludeIO", "PreludeList", "PreludeMonad",
    "PreludeText", "Regexp"
  },
  symas={}, symcs={}, symis={},
  symts={
    @Meta.SymT(
      offset=67, name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.Generics", base="ArrayList"),
      typ=0, kind=16, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=181,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Generics", base="ArrayList", member="new"
          ),
          stri="s(s)", sig=2, nativ="new", depth=1, rkind=9
        ),
        @Meta.SymV(
          offset=123,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Generics", base="ArrayList", member="add"
          ),
          stri="s(ss)", sig=5, nativ="add", depth=2, rkind=9
        ),
        @Meta.SymV(
          offset=223,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Generics", base="ArrayList", member="fromFregeList"
          ),
          stri="s(u)", sig=7, depth=1, rkind=13
        )
      },
      nativ="java.util.ArrayList", gargs={2}
    )
  },
  symvs={}, symls={},
  taus={
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.Generics", base="ArrayList")}
    ),
    @Meta.Tau(kind=9), @Meta.Tau(suba=1, tvar="a"), @Meta.Tau(kind=0, suba=0, subb=2),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="()")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="ST")}),
    @Meta.Tau(suba=1, tvar="s"), @Meta.Tau(kind=0, suba=5, subb=6),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeIO", base="Mutable")}),
    @Meta.Tau(kind=0, suba=8, subb=6), @Meta.Tau(kind=0, suba=9, subb=3), @Meta.Tau(kind=0, suba=7, subb=10),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Bool")}),
    @Meta.Tau(kind=0, suba=7, subb=12),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="[]")}),
    @Meta.Tau(kind=0, suba=14, subb=2), @Meta.Tau(kind=8, suba=1, subb=1)
  },
  rhos={
    @Meta.Rho(rhofun=false, rhotau=3), @Meta.Rho(rhofun=false, rhotau=4), @Meta.Rho(rhofun=false, rhotau=11),
    @Meta.Rho(sigma=1, rhotau=2), @Meta.Rho(rhofun=false, rhotau=10), @Meta.Rho(rhofun=false, rhotau=2),
    @Meta.Rho(rhofun=false, rhotau=13), @Meta.Rho(sigma=4, rhotau=6), @Meta.Rho(sigma=3, rhotau=7),
    @Meta.Rho(rhofun=false, rhotau=15), @Meta.Rho(sigma=6, rhotau=2)
  },
  sigmas={
    @Meta.Sigma(bound={"a"}, kinds={1}, rho=0), @Meta.Sigma(rho=1), @Meta.Sigma(bound={"a", "s"}, kinds={1, 1}, rho=3),
    @Meta.Sigma(rho=4), @Meta.Sigma(rho=5), @Meta.Sigma(bound={"a", "s"}, kinds={1, 1}, rho=8), @Meta.Sigma(rho=9),
    @Meta.Sigma(bound={"a", "s"}, kinds={1, 1}, rho=10)
  },
  exprs={@Meta.Expr()}
)
final public class Generics  {
  



final public static class TArrayList  {
  @SuppressWarnings("unchecked") final public static <𝓐, 𝓢> Func.U<𝓢, java.util.ArrayList<𝓐>> $new(
    final short arg$1
  ) {
    return (Func.U<𝓢, java.util.ArrayList<𝓐>>)((final Lazy<𝓢> _state) -> {
              return Thunk.<java.util.ArrayList<𝓐>>lazy((java.util.ArrayList<𝓐>)new java.util.ArrayList<𝓐>());
            });
  }
  final public static <𝓐, 𝓢> Func.U<𝓢, Boolean> add(final java.util.ArrayList<𝓐> arg$1, final 𝓐 arg$2) {
    return (Func.U<𝓢, Boolean>)((final Lazy<𝓢> _state) -> {
              return Thunk.<Boolean>lazy(arg$1.add(arg$2));
            });
  }
  final public static <𝓐, 𝓢> Func.U<𝓢, java.util.ArrayList<𝓐>> fromFregeList(final Lazy<PreludeBase.TList<𝓐>> arg$1) {
    return (Func.U<𝓢, java.util.ArrayList<𝓐>>)((final Lazy<𝓢> arg$7711) -> {
              final java.util.ArrayList<𝓐> v2056$7695 = TArrayList.<𝓐, 𝓢>$new(PreludeBase.TUnit.Unit)
              .apply(arg$7711).call();
              final Func.U<𝓢, java.util.ArrayList<𝓐>> v2057$7696 = Generics.<𝓐, 𝓢>go(
                    arg$1.call(), Thunk.<java.util.ArrayList<𝓐>>lazy(v2056$7695)
                  );
              return Thunk.<java.util.ArrayList<𝓐>>nested(
                        (Lazy<Lazy<java.util.ArrayList<𝓐>>>)(() -> v2057$7696.apply(arg$7711))
                      );
            });
  }
}
final public static <𝓐, 𝓢> Func.U<𝓢, java.util.ArrayList<𝓐>> go(
  final PreludeBase.TList<𝓐> arg$1, final Lazy<java.util.ArrayList<𝓐>> arg$2
) {
  final PreludeBase.TList.DCons<𝓐> $7714 = arg$1.asCons();
  if ($7714 != null) {
    final 𝓐 µ$$7621 = $7714.mem1.call();
    return (Func.U<𝓢, java.util.ArrayList<𝓐>>)((final Lazy<𝓢> arg$7716) -> {
              final boolean v4796$7671 = (boolean)TArrayList.<𝓐, 𝓢>add(arg$2.call(), µ$$7621)
              .apply(arg$7716).call();
              final Func.U<𝓢, java.util.ArrayList<𝓐>> v4797$7672 = Thunk.<Func.U<𝓢, java.util.ArrayList<𝓐>>>shared(
                    (Lazy<Func.U<𝓢, java.util.ArrayList<𝓐>>>)(() -> Generics.<𝓐, 𝓢>go(
                              $7714.mem2.call(), arg$2
                            ))
                  ).call();
              return Thunk.<java.util.ArrayList<𝓐>>nested(
                        (Lazy<Lazy<java.util.ArrayList<𝓐>>>)(() -> v4797$7672.apply(arg$7716))
                      );
            });
  }
  final PreludeBase.TList.DList<𝓐> $7719 = arg$1.asList();
  assert $7719 != null;
  return PreludeMonad.IMonad_ST.<𝓢, java.util.ArrayList<𝓐>>pure(arg$2);
}

}
