/*
  Source code is in UTF-8 encoding. The following symbols may appear, among others:
  α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω « • ¦ » ∀ ∃ ∷ … → ← ﬁ ﬂ ƒ
  If you can't read this, you're out of luck. This code was generated with the frege compiler version 3.25.84
  from
  /Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/compile/CompileGlobal.fr
  Do not edit this file! Instead, edit the source file and recompile.
*/

package ch.fhnw.thga.fregelanguageserver.compile;

import frege.run8.Func;
import frege.run8.Lazy;
import frege.run8.Thunk;
import frege.run.Kind;
import frege.run.RunTM;
import frege.runtime.Meta;
import frege.runtime.Phantom.RealWorld;
import ch.fhnw.thga.fregelanguageserver.compile.CompileOptions;
import frege.Prelude;
import frege.Version;
import frege.compiler.Classtools;
import frege.compiler.common.CompilerOptions;
import frege.compiler.common.Roman;
import frege.compiler.enums.CaseKind;
import frege.compiler.enums.Flags;
import frege.compiler.enums.Literals;
import frege.compiler.enums.RFlag;
import frege.compiler.enums.SymState;
import frege.compiler.enums.TokenID;
import frege.compiler.enums.Visibility;
import frege.compiler.instances.PositionedSName;
import frege.compiler.types.AbstractJava;
import frege.compiler.types.ConstructorField;
import frege.compiler.types.Expression;
import frege.compiler.types.External;
import frege.compiler.types.Global;
import frege.compiler.types.ImportDetails;
import frege.compiler.types.JNames;
import frege.compiler.types.NSNames;
import frege.compiler.types.Packs;
import frege.compiler.types.Patterns;
import frege.compiler.types.Positions;
import frege.compiler.types.QNames;
import frege.compiler.types.SNames;
import frege.compiler.types.SourceDefinitions;
import frege.compiler.types.Strictness;
import frege.compiler.types.Symbols;
import frege.compiler.types.Targets;
import frege.compiler.types.Tokens;
import frege.compiler.types.Types;
import frege.control.Category;
import frege.control.Semigroupoid;
import frege.control.monad.State;
import frege.control.monad.trans.MonadIO;
import frege.control.monad.trans.MonadTrans;
import frege.data.Bits;
import frege.data.Foldable;
import frege.data.Monoid;
import frege.data.Traversable;
import frege.data.TreeMap;
import frege.data.Tuples;
import frege.data.wrapper.Const;
import frege.data.wrapper.Dual;
import frege.data.wrapper.Endo;
import frege.data.wrapper.Identity;
import frege.data.wrapper.Num;
import frege.java.IO;
import frege.java.Lang;
import frege.java.Net;
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
  source="/Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/compile/CompileGlobal.fr",
  time=1659200052355L, jmajor=11, jminor=-1,
  imps={
    "ch.fhnw.thga.fregelanguageserver.compile.CompileOptions", "frege.compiler.common.CompilerOptions",
    "frege.compiler.types.Global", "frege.Prelude", "frege.prelude.PreludeArrays", "frege.prelude.PreludeBase",
    "frege.prelude.PreludeDecimal", "frege.prelude.PreludeIO", "frege.prelude.PreludeList",
    "frege.prelude.PreludeMonad", "frege.prelude.PreludeText", "frege.java.util.Regex"
  },
  nmss={
    "CompileOptions", "CompilerOptions", "Global", "Prelude", "PreludeArrays", "PreludeBase", "PreludeDecimal",
    "PreludeIO", "PreludeList", "PreludeMonad", "PreludeText", "Regexp"
  },
  symas={}, symcs={}, symis={}, symts={},
  symvs={
    @Meta.SymV(
      offset=530,
      name=@Meta.QName(pack="ch.fhnw.thga.fregelanguageserver.compile.CompileGlobal", base="standardCompileGlobal"),
      stri="u", sig=0, depth=0, rkind=8
    ),
    @Meta.SymV(
      offset=284, name=@Meta.QName(pack="ch.fhnw.thga.fregelanguageserver.compile.CompileGlobal", base="fromOptions"),
      stri="s(s)", sig=2, depth=1, rkind=13
    )
  },
  symls={},
  taus={
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="ST")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="RealWorld")}),
    @Meta.Tau(kind=0, suba=0, subb=1),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.compiler.types.Global", base="Global")}),
    @Meta.Tau(kind=0, suba=2, subb=3),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.compiler.types.Global", base="Options")})
  },
  rhos={@Meta.Rho(rhofun=false, rhotau=4), @Meta.Rho(rhofun=false, rhotau=5), @Meta.Rho(sigma=1, rhotau=0)},
  sigmas={@Meta.Sigma(rho=0), @Meta.Sigma(rho=1), @Meta.Sigma(rho=2)}, exprs={@Meta.Expr()}
)
final public class CompileGlobal  {
  




final public static Func.U<RealWorld, Global.TGlobal> fromOptions(final Global.TOptions arg$1) {
  return (Func.U<RealWorld, Global.TGlobal>)((final Lazy<RealWorld> arg$13593) -> {
            final java.net.URLClassLoader v2056$13557 = CompilerOptions.theClassLoader(
                  arg$1
                ).apply(arg$13593).call();
            final Func.U<RealWorld, Global.TGlobal> v2053$13576 = CompilerOptions.standardGlobal
            .call();
            final Global.TGlobal v2056$13579 = v2053$13576.apply(arg$13593).call();
            final Func.U<RealWorld, Global.TGlobal> v2057$13580 = PreludeMonad.IMonad_ST.<
              RealWorld, Global.TGlobal
            >pure(
                  Thunk.<Global.TGlobal>shared(
                        (Lazy<Global.TGlobal>)(() -> Global.TGlobal.chg$sub(
                                  Global.TGlobal.upd$options(v2056$13579, arg$1),
                                  (Func.U<Global.TSubSt, Global.TSubSt>)((final Lazy<Global.TSubSt> η$13597) -> Thunk.<
                                        Global.TSubSt
                                      >nested(
                                            (Lazy<Lazy<Global.TSubSt>>)(() -> PreludeBase.<
                                                  Global.TSubSt, Global.TSubSt, java.net.URLClassLoader
                                                >flip(
                                                      (Func.U<Global.TSubSt, Func.U<java.net.URLClassLoader, Global.TSubSt>>)((
                                                        final Lazy<Global.TSubSt> η$13598
                                                      ) -> (Func.U<java.net.URLClassLoader, Global.TSubSt>)((
                                                            final Lazy<java.net.URLClassLoader> η$13599
                                                          ) -> Thunk.<Global.TSubSt>shared(
                                                                    (Lazy<Global.TSubSt>)(() -> Global.TSubSt.upd$loader(
                                                                              η$13598.call(), η$13599.call()
                                                                            ))
                                                                  ))),
                                                      Thunk.<java.net.URLClassLoader>lazy(v2056$13557), η$13597
                                                    ))
                                          ))
                                ))
                      )
                );
            return Thunk.<Global.TGlobal>nested((Lazy<Lazy<Global.TGlobal>>)(() -> v2057$13580.apply(arg$13593)));
          });
}
final public static Lazy<Func.U<RealWorld, Global.TGlobal>> standardCompileGlobal = Thunk.<
  Func.U<RealWorld, Global.TGlobal>
>shared(
      (Lazy<Func.U<RealWorld, Global.TGlobal>>)(() -> {
            return CompileGlobal.fromOptions(CompileOptions.standardCompileOptions.call());
          })
    );

}
