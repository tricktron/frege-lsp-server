/*
  Source code is in UTF-8 encoding. The following symbols may appear, among others:
  α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω « • ¦ » ∀ ∃ ∷ … → ← ﬁ ﬂ ƒ
  If you can't read this, you're out of luck. This code was generated with the frege compiler version 3.25.84
  from /Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/hover/HoverLSP.fr
  Do not edit this file! Instead, edit the source file and recompile.
*/

package ch.fhnw.thga.fregelanguageserver.hover;

import frege.run8.Func;
import frege.run8.Lazy;
import frege.run8.Thunk;
import frege.run.Kind;
import frege.run.RunTM;
import frege.runtime.Meta;
import frege.runtime.Phantom.RealWorld;
import ch.fhnw.thga.fregelanguageserver.compiler.CompilerHelper;
import ch.fhnw.thga.fregelanguageserver.diagnostic.Diagnostic;
import ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic;
import ch.fhnw.thga.fregelanguageserver.hover.Hover;
import frege.Prelude;
import frege.Version;
import frege.compiler.Classes;
import frege.compiler.Classtools;
import frege.compiler.GenMeta;
import frege.compiler.Javatypes;
import frege.compiler.Kinds;
import frege.compiler.Main;
import frege.compiler.Typecheck;
import frege.compiler.Utilities;
import frege.compiler.classes.Nice;
import frege.compiler.classes.QNameMatcher;
import frege.compiler.common.Annotate;
import frege.compiler.common.AnnotateG;
import frege.compiler.common.Binders;
import frege.compiler.common.CompilerOptions;
import frege.compiler.common.Desugar;
import frege.compiler.common.Errors;
import frege.compiler.common.ImpExp;
import frege.compiler.common.JavaName;
import frege.compiler.common.Mangle;
import frege.compiler.common.PatternCompiler;
import frege.compiler.common.Resolve;
import frege.compiler.common.Roman;
import frege.compiler.common.SymbolTable;
import frege.compiler.common.Trans;
import frege.compiler.common.Tuples;
import frege.compiler.common.Types;
import frege.compiler.common.UnAlias;
import frege.compiler.enums.CaseKind;
import frege.compiler.enums.Flags;
import frege.compiler.enums.Literals;
import frege.compiler.enums.RFlag;
import frege.compiler.enums.SymState;
import frege.compiler.enums.TokenID;
import frege.compiler.enums.Visibility;
import frege.compiler.gen.java.Bindings;
import frege.compiler.gen.java.Common;
import frege.compiler.gen.java.Constants;
import frege.compiler.gen.java.DataCode;
import frege.compiler.gen.java.InstanceCode;
import frege.compiler.gen.java.Instantiation;
import frege.compiler.gen.java.Match;
import frege.compiler.gen.java.MethodCall;
import frege.compiler.gen.java.PrettyJava;
import frege.compiler.gen.java.VarCode;
import frege.compiler.grammar.Frege;
import frege.compiler.grammar.Lexer;
import frege.compiler.instances.NiceExprS;
import frege.compiler.instances.Nicer;
import frege.compiler.instances.PositionedSName;
import frege.compiler.passes.Easy;
import frege.compiler.passes.Enter;
import frege.compiler.passes.Fields;
import frege.compiler.passes.Final;
import frege.compiler.passes.Fix;
import frege.compiler.passes.GenCode;
import frege.compiler.passes.GlobalLam;
import frege.compiler.passes.Imp;
import frege.compiler.passes.Instances;
import frege.compiler.passes.LetUnroll;
import frege.compiler.passes.Strict;
import frege.compiler.passes.Transdef;
import frege.compiler.passes.TypeAlias;
import frege.compiler.tc.Methods;
import frege.compiler.tc.Patterns;
import frege.compiler.tc.Util;
import frege.compiler.types.AbstractJava;
import frege.compiler.types.ConstructorField;
import frege.compiler.types.Expression;
import frege.compiler.types.External;
import frege.compiler.types.Global;
import frege.compiler.types.ImportDetails;
import frege.compiler.types.JNames;
import frege.compiler.types.NSNames;
import frege.compiler.types.Packs;
import frege.compiler.types.Positions;
import frege.compiler.types.QNames;
import frege.compiler.types.SNames;
import frege.compiler.types.SourceDefinitions;
import frege.compiler.types.Strictness;
import frege.compiler.types.Symbols;
import frege.compiler.types.Targets;
import frege.compiler.types.Tokens;
import frege.control.Category;
import frege.control.Concurrent;
import frege.control.Semigroupoid;
import frege.control.monad.State;
import frege.control.monad.trans.MonadIO;
import frege.control.monad.trans.MonadTrans;
import frege.data.Bits;
import frege.data.Char;
import frege.data.Foldable;
import frege.data.Graph;
import frege.data.List;
import frege.data.Monoid;
import frege.data.Traversable;
import frege.data.Tree;
import frege.data.TreeMap;
import frege.data.wrapper.Const;
import frege.data.wrapper.Dual;
import frege.data.wrapper.Endo;
import frege.data.wrapper.Identity;
import frege.data.wrapper.Num;
import frege.java.IO;
import frege.java.Lang;
import frege.java.Net;
import frege.java.util.Regex;
import frege.java.util.Zip;
import frege.lib.Modules;
import frege.lib.PP;
import frege.prelude.Maybe;
import frege.prelude.PreludeArrays;
import frege.prelude.PreludeBase;
import frege.prelude.PreludeDecimal;
import frege.prelude.PreludeIO;
import frege.prelude.PreludeList;
import frege.prelude.PreludeMonad;
import frege.prelude.PreludeText;
import frege.system.Random;
import frege.test.QuickCheck;
import frege.test.QuickCheckArbitrary;
import frege.test.QuickCheckException;
import frege.test.QuickCheckGen;
import frege.test.QuickCheckModifiers;
import frege.test.QuickCheckProperty;
import frege.test.QuickCheckState;
import frege.test.QuickCheckTest;
import frege.test.QuickCheckText;

@SuppressWarnings("unused")
@Meta.FregePackage(
  source="/Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/hover/HoverLSP.fr",
  time=1657030861534L, jmajor=11, jminor=-1,
  imps={
    "ch.fhnw.thga.fregelanguageserver.hover.Hover", "ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic",
    "frege.Prelude", "frege.prelude.PreludeArrays", "frege.prelude.PreludeBase", "frege.prelude.PreludeDecimal",
    "frege.prelude.PreludeIO", "frege.prelude.PreludeList", "frege.prelude.PreludeMonad", "frege.prelude.PreludeText",
    "frege.java.util.Regex"
  },
  nmss={
    "Hover", "LSPDiagnostic", "Prelude", "PreludeArrays", "PreludeBase", "PreludeDecimal", "PreludeIO",
    "PreludeList", "PreludeMonad", "PreludeText", "Regexp"
  },
  symas={}, symcs={}, symis={},
  symts={
    @Meta.SymT(
      offset=387,
      name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="MarkupKindLSP"), typ=0,
      kind=4, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=533,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="MarkupKindLSP", member="markdown"
          ),
          stri="s", sig=1, nativ="org.eclipse.lsp4j.MarkupKind.MARKDOWN", depth=0, rkind=9
        ),
        @Meta.SymV(
          offset=461,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="MarkupKindLSP", member="plaintext"
          ),
          stri="s", sig=1, nativ="org.eclipse.lsp4j.MarkupKind.PLAINTEXT", depth=0, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.MarkupKind"
    ),
    @Meta.SymT(
      offset=252,
      name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="MarkupContentLSP"), typ=2,
      kind=4, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=337,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="MarkupContentLSP", member="new"
          ),
          stri="s(ss)", sig=3, nativ="new", pur=true, depth=2, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.MarkupContent"
    ),
    @Meta.SymT(
      offset=600, name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="HoverLSP"),
      typ=4, kind=4, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=722,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="HoverLSP", member="hoverToHoverLSP"
          ),
          stri="s(s(ss))", sig=6, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=669,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="HoverLSP", member="new"
          ),
          stri="s(ss)", sig=8, nativ="new", pur=true, depth=2, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.Hover"
    )
  },
  symvs={
    @Meta.SymV(
      offset=941,
      name=@Meta.QName(
        pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="compileAndGetTypeSignatureOnHoverLSP"
      ),
      stri="s(uu)", sig=10, depth=2, rkind=13
    )
  },
  symls={},
  taus={
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="MarkupKindLSP")}
    ),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="StringJ")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Char")}),
    @Meta.Tau(kind=0, suba=1, subb=2), @Meta.Tau(kind=9),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="MarkupContentLSP")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.hover.HoverLSP", base="HoverLSP")}
    ),
    @Meta.Tau(
      kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.hover.Hover", base="Hover")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="RangeLSP")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="PositionLSP")}
    ),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="ST")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="RealWorld")}),
    @Meta.Tau(kind=0, suba=10, subb=11),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Maybe")}),
    @Meta.Tau(kind=0, suba=13, subb=6), @Meta.Tau(kind=0, suba=12, subb=14)
  },
  rhos={
    @Meta.Rho(rhofun=false, rhotau=0), @Meta.Rho(rhofun=false, rhotau=3), @Meta.Rho(rhofun=false, rhotau=5),
    @Meta.Rho(sigma=1, rhotau=2), @Meta.Rho(sigma=1, rhotau=3), @Meta.Rho(rhofun=false, rhotau=6),
    @Meta.Rho(rhofun=false, rhotau=7), @Meta.Rho(sigma=5, rhotau=5), @Meta.Rho(rhofun=false, rhotau=8),
    @Meta.Rho(sigma=7, rhotau=5), @Meta.Rho(sigma=2, rhotau=9), @Meta.Rho(rhofun=false, rhotau=9),
    @Meta.Rho(rhofun=false, rhotau=15), @Meta.Rho(sigma=9, rhotau=12), @Meta.Rho(sigma=1, rhotau=13)
  },
  sigmas={
    @Meta.Sigma(rho=0), @Meta.Sigma(rho=1), @Meta.Sigma(rho=2), @Meta.Sigma(rho=4), @Meta.Sigma(rho=5),
    @Meta.Sigma(rho=6), @Meta.Sigma(rho=7), @Meta.Sigma(rho=8), @Meta.Sigma(rho=10), @Meta.Sigma(rho=11),
    @Meta.Sigma(rho=14)
  },
  exprs={@Meta.Expr()}
)
final public class HoverLSP  {
  



final public static class THoverLSP  {
  final public static org.eclipse.lsp4j.Hover hoverToHoverLSP(final Hover.THover arg$1) {
    final Hover.TFregeCodeBlock content$18209 = arg$1.mem$content.call();
    final Diagnostic.TRange range$18208 = arg$1.mem$range.call();
    return new org.eclipse.lsp4j.Hover(
          new org.eclipse.lsp4j.MarkupContent(
            org.eclipse.lsp4j.MarkupKind.MARKDOWN, Hover.IShow_FregeCodeBlock.show(content$18209)
          ),
          LSPDiagnostic.TRangeLSP.fromRange(range$18208)
        );
  }
}
final public static Func.U<RealWorld, PreludeBase.TMaybe<org.eclipse.lsp4j.Hover>> compileAndGetTypeSignatureOnHoverLSP(
  final Lazy<String/*<Character>*/> arg$1, final Lazy<org.eclipse.lsp4j.Position> arg$2
) {
  return (Func.U<RealWorld, PreludeBase.TMaybe<org.eclipse.lsp4j.Hover>>)((final Lazy<RealWorld> arg$18275) -> {
            final PreludeBase.TMaybe<Hover.THover> v2056$18252 = Hover.compileAndGetTypeSignatureOnHover(
                  arg$1,
                  Thunk.<Diagnostic.TPosition>shared(
                        (Lazy<Diagnostic.TPosition>)(() -> LSPDiagnostic.TPositionLSP.toPosition(
                                  arg$2
                                ))
                      )
                ).apply(arg$18275).call();
            final Func.U<RealWorld, PreludeBase.TMaybe<org.eclipse.lsp4j.Hover>> v2057$18253 =
            PreludeMonad.IMonad_ST.<RealWorld, PreludeBase.TMaybe<org.eclipse.lsp4j.Hover>>pure(
                  Thunk.<PreludeBase.TMaybe<org.eclipse.lsp4j.Hover>>shared(
                        (Lazy<PreludeBase.TMaybe<org.eclipse.lsp4j.Hover>>)(() -> Maybe.IFunctor_Maybe.<
                              Hover.THover, org.eclipse.lsp4j.Hover
                            >fmap(
                                  (Func.U<Hover.THover, org.eclipse.lsp4j.Hover>)((final Lazy<Hover.THover> η$18277) -> Thunk.<
                                        org.eclipse.lsp4j.Hover
                                      >shared(
                                            (Lazy<org.eclipse.lsp4j.Hover>)(() -> THoverLSP.hoverToHoverLSP(
                                                      η$18277.call()
                                                    ))
                                          )),
                                  v2056$18252
                                ))
                      )
                );
            return Thunk.<PreludeBase.TMaybe<org.eclipse.lsp4j.Hover>>nested(
                      (Lazy<Lazy<PreludeBase.TMaybe<org.eclipse.lsp4j.Hover>>>)(() -> v2057$18253
                          .apply(arg$18275))
                    );
          });
}

}