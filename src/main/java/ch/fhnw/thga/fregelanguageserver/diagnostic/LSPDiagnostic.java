/*
  Source code is in UTF-8 encoding. The following symbols may appear, among others:
  α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω « • ¦ » ∀ ∃ ∷ … → ← ﬁ ﬂ ƒ
  If you can't read this, you're out of luck. This code was generated with the frege compiler version 3.25.84
  from
  /Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/diagnostic/LSPDiagnostic.fr
  Do not edit this file! Instead, edit the source file and recompile.
*/

package ch.fhnw.thga.fregelanguageserver.diagnostic;

import frege.run8.Func;
import frege.run8.Lazy;
import frege.run8.Thunk;
import frege.run.Kind;
import frege.run.RunTM;
import frege.runtime.Meta;
import frege.runtime.Phantom.RealWorld;
import ch.fhnw.thga.fregelanguageserver.compiler.CompilerHelper;
import ch.fhnw.thga.fregelanguageserver.diagnostic.Diagnostic;
import ch.fhnw.thga.fregelanguageserver.lsp4j.PositionLSP4J;
import ch.fhnw.thga.fregelanguageserver.lsp4j.RangeLSP4J;
import ch.fhnw.thga.fregelanguageserver.types.Position;
import ch.fhnw.thga.fregelanguageserver.types.Range;
import frege.Prelude;
import frege.Version;
import frege.compiler.Classes;
import frege.compiler.Classtools;
import frege.compiler.Javatypes;
import frege.compiler.Kinds;
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
import frege.compiler.gen.java.Common;
import frege.compiler.gen.java.PrettyJava;
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
  source="/Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/diagnostic/LSPDiagnostic.fr",
  time=1657089552190L, jmajor=11, jminor=-1,
  imps={
    "ch.fhnw.thga.fregelanguageserver.diagnostic.Diagnostic", "ch.fhnw.thga.fregelanguageserver.types.Position",
    "ch.fhnw.thga.fregelanguageserver.lsp4j.PositionLSP4J", "frege.Prelude", "frege.prelude.PreludeArrays",
    "frege.prelude.PreludeBase", "frege.prelude.PreludeDecimal", "frege.prelude.PreludeIO", "frege.prelude.PreludeList",
    "frege.prelude.PreludeMonad", "frege.prelude.PreludeText", "ch.fhnw.thga.fregelanguageserver.types.Range",
    "ch.fhnw.thga.fregelanguageserver.lsp4j.RangeLSP4J", "frege.java.util.Regex"
  },
  nmss={
    "Diagnostic", "Position", "PositionLSP4J", "Prelude", "PreludeArrays", "PreludeBase", "PreludeDecimal",
    "PreludeIO", "PreludeList", "PreludeMonad", "PreludeText", "Range", "RangeLSP4J", "Regexp"
  },
  symas={}, symcs={}, symis={},
  symts={
    @Meta.SymT(
      offset=473,
      name=@Meta.QName(
        kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticSeverityLSP"
      ),
      typ=0, kind=2, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=672,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticSeverityLSP",
            member="warning"
          ),
          stri="s", sig=0, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Warning", pur=true,
          depth=0, rkind=9
        ),
        @Meta.SymV(
          offset=880,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticSeverityLSP",
            member="hint"
          ),
          stri="s", sig=0, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Hint", pur=true, depth=0,
          rkind=9
        ),
        @Meta.SymV(
          offset=568,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticSeverityLSP",
            member="error"
          ),
          stri="s", sig=0, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Error", pur=true, depth=0,
          rkind=9
        ),
        @Meta.SymV(
          offset=973,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticSeverityLSP",
            member="fromCompilerSeverity"
          ),
          stri="s(s)", sig=2, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=776,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticSeverityLSP",
            member="information"
          ),
          stri="s", sig=0, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Information", pur=true,
          depth=0, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.DiagnosticSeverity"
    ),
    @Meta.SymT(
      offset=1246,
      name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticLSP"),
      typ=3, kind=2, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=1408,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticLSP",
            member="fromDiagnostic"
          ),
          stri="s(s(ssss))", sig=5, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=1325,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticLSP",
            member="new"
          ),
          stri="s(ssss)", sig=8, nativ="new", pur=true, depth=4, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.Diagnostic"
    ),
    @Meta.SymT(
      offset=1690,
      name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="ArrayList"),
      typ=9, kind=24, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=1804,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="ArrayList",
            member="new"
          ),
          stri="s(s)", sig=11, nativ="new", depth=1, rkind=9
        ),
        @Meta.SymV(
          offset=1746,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="ArrayList",
            member="add"
          ),
          stri="s(ss)", sig=14, nativ="add", depth=2, rkind=9
        ),
        @Meta.SymV(
          offset=1846,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="ArrayList",
            member="fromFregeList"
          ),
          stri="s(u)", sig=16, depth=1, rkind=13
        )
      },
      nativ="java.util.ArrayList", gargs={10}
    )
  },
  symvs={
    @Meta.SymV(
      offset=2110,
      name=@Meta.QName(
        pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="compileAndGetDiagnosticsLSP"
      ),
      stri="s(u)", sig=17, depth=1, rkind=13
    )
  },
  symls={},
  taus={
    @Meta.Tau(
      kind=2, suba=0,
      tcon={
        @Meta.QName(
          kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticSeverityLSP"
        )
      }
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={
        @Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.Diagnostic", base="DiagnosticSeverity")
      }
    ),
    @Meta.Tau(kind=9),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="DiagnosticLSP")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.Diagnostic", base="Diagnostic")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.lsp4j.RangeLSP4J", base="RangeLSP")}
    ),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="StringJ")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Char")}),
    @Meta.Tau(kind=0, suba=6, subb=7),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.LSPDiagnostic", base="ArrayList")}
    ),
    @Meta.Tau(suba=2, tvar="a"), @Meta.Tau(kind=0, suba=9, subb=10),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="()")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="ST")}),
    @Meta.Tau(suba=2, tvar="s"), @Meta.Tau(kind=0, suba=13, subb=14),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeIO", base="Mutable")}),
    @Meta.Tau(kind=0, suba=16, subb=14), @Meta.Tau(kind=0, suba=17, subb=11), @Meta.Tau(kind=0, suba=15, subb=18),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Bool")}),
    @Meta.Tau(kind=0, suba=15, subb=20),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="[]")}),
    @Meta.Tau(kind=0, suba=22, subb=10), @Meta.Tau(kind=8, suba=2, subb=2),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="RealWorld")}),
    @Meta.Tau(kind=0, suba=13, subb=25), @Meta.Tau(kind=0, suba=16, subb=25), @Meta.Tau(kind=0, suba=9, subb=3),
    @Meta.Tau(kind=0, suba=27, subb=28), @Meta.Tau(kind=0, suba=26, subb=29)
  },
  rhos={
    @Meta.Rho(rhofun=false, rhotau=0), @Meta.Rho(rhofun=false, rhotau=1), @Meta.Rho(sigma=1, rhotau=0),
    @Meta.Rho(rhofun=false, rhotau=3), @Meta.Rho(rhofun=false, rhotau=4), @Meta.Rho(sigma=4, rhotau=3),
    @Meta.Rho(rhofun=false, rhotau=5), @Meta.Rho(rhofun=false, rhotau=8), @Meta.Rho(sigma=7, rhotau=3),
    @Meta.Rho(sigma=0, rhotau=8), @Meta.Rho(sigma=7, rhotau=9), @Meta.Rho(sigma=6, rhotau=10),
    @Meta.Rho(rhofun=false, rhotau=11), @Meta.Rho(rhofun=false, rhotau=12), @Meta.Rho(rhofun=false, rhotau=19),
    @Meta.Rho(sigma=10, rhotau=14), @Meta.Rho(rhofun=false, rhotau=18), @Meta.Rho(rhofun=false, rhotau=10),
    @Meta.Rho(rhofun=false, rhotau=21), @Meta.Rho(sigma=13, rhotau=18), @Meta.Rho(sigma=12, rhotau=19),
    @Meta.Rho(rhofun=false, rhotau=23), @Meta.Rho(sigma=15, rhotau=14), @Meta.Rho(rhofun=false, rhotau=30),
    @Meta.Rho(sigma=7, rhotau=23)
  },
  sigmas={
    @Meta.Sigma(rho=0), @Meta.Sigma(rho=1), @Meta.Sigma(rho=2), @Meta.Sigma(rho=3), @Meta.Sigma(rho=4),
    @Meta.Sigma(rho=5), @Meta.Sigma(rho=6), @Meta.Sigma(rho=7), @Meta.Sigma(rho=11),
    @Meta.Sigma(bound={"a"}, kinds={2}, rho=12), @Meta.Sigma(rho=13),
    @Meta.Sigma(bound={"a", "s"}, kinds={2, 2}, rho=15), @Meta.Sigma(rho=16), @Meta.Sigma(rho=17),
    @Meta.Sigma(bound={"a", "s"}, kinds={2, 2}, rho=20), @Meta.Sigma(rho=21),
    @Meta.Sigma(bound={"a", "s"}, kinds={2, 2}, rho=22), @Meta.Sigma(rho=24)
  },
  exprs={@Meta.Expr()}
)
final public class LSPDiagnostic  {
  



final public static class TDiagnosticSeverityLSP  {
  final public static org.eclipse.lsp4j.DiagnosticSeverity fromCompilerSeverity(final short arg$1) {
    if (arg$1 == Diagnostic.TDiagnosticSeverity.HINT) {
      return org.eclipse.lsp4j.DiagnosticSeverity.Hint;
    }
    if (arg$1 == Diagnostic.TDiagnosticSeverity.WARNING) {
      return org.eclipse.lsp4j.DiagnosticSeverity.Warning;
    }
    if (arg$1 == Diagnostic.TDiagnosticSeverity.ERROR) {
      return org.eclipse.lsp4j.DiagnosticSeverity.Error;
    }
    assert arg$1 == Diagnostic.TDiagnosticSeverity.INFORMATION;
    return org.eclipse.lsp4j.DiagnosticSeverity.Information;
  }
}
final public static class TDiagnosticLSP  {
  final public static org.eclipse.lsp4j.Diagnostic fromDiagnostic(final Diagnostic.TDiagnostic arg$1) {
    final String/*<Character>*/ message$17363 = arg$1.mem$message.call();
    final String/*<Character>*/ source$17365 = arg$1.mem$source.call();
    final short severity$17364 = (short)arg$1.mem$severity.call();
    final Range.TRange range$17362 = arg$1.mem$range.call();
    return new org.eclipse.lsp4j.Diagnostic(
          RangeLSP4J.TRangeLSP.fromRange(range$17362), message$17363,
          TDiagnosticSeverityLSP.fromCompilerSeverity(severity$17364), source$17365
        );
  }
}
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
    return (Func.U<𝓢, java.util.ArrayList<𝓐>>)((final Lazy<𝓢> arg$17516) -> {
              final java.util.ArrayList<𝓐> v2056$17496 = TArrayList.<𝓐, 𝓢>$new(PreludeBase.TUnit.Unit)
              .apply(arg$17516).call();
              final Func.U<𝓢, java.util.ArrayList<𝓐>> v2057$17497 = LSPDiagnostic.<𝓐, 𝓢>go(
                    arg$1.call(), Thunk.<java.util.ArrayList<𝓐>>lazy(v2056$17496)
                  );
              return Thunk.<java.util.ArrayList<𝓐>>nested(
                        (Lazy<Lazy<java.util.ArrayList<𝓐>>>)(() -> v2057$17497.apply(
                                  arg$17516
                                ))
                      );
            });
  }
}
final public static <𝓐, 𝓢> Func.U<𝓢, java.util.ArrayList<𝓐>> go(
  final PreludeBase.TList<𝓐> arg$1, final Lazy<java.util.ArrayList<𝓐>> arg$2
) {
  final PreludeBase.TList.DCons<𝓐> $17519 = arg$1.asCons();
  if ($17519 != null) {
    final 𝓐 µ$$17378 = $17519.mem1.call();
    return (Func.U<𝓢, java.util.ArrayList<𝓐>>)((final Lazy<𝓢> arg$17521) -> {
              final boolean v4796$17450 = (boolean)TArrayList.<𝓐, 𝓢>add(arg$2.call(), µ$$17378)
              .apply(arg$17521).call();
              final Func.U<𝓢, java.util.ArrayList<𝓐>> v4797$17451 = Thunk.<Func.U<𝓢, java.util.ArrayList<𝓐>>>shared(
                    (Lazy<Func.U<𝓢, java.util.ArrayList<𝓐>>>)(() -> LSPDiagnostic.<𝓐, 𝓢>go(
                              $17519.mem2.call(), arg$2
                            ))
                  ).call();
              return Thunk.<java.util.ArrayList<𝓐>>nested(
                        (Lazy<Lazy<java.util.ArrayList<𝓐>>>)(() -> v4797$17451.apply(
                                  arg$17521
                                ))
                      );
            });
  }
  final PreludeBase.TList.DList<𝓐> $17524 = arg$1.asList();
  assert $17524 != null;
  return PreludeMonad.IMonad_ST.<𝓢, java.util.ArrayList<𝓐>>pure(arg$2);
}
final public static Func.U<RealWorld, java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>> compileAndGetDiagnosticsLSP(
  final Lazy<String/*<Character>*/> arg$1
) {
  return (Func.U<RealWorld, java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>>)((final Lazy<RealWorld> arg$17525) -> {
            final PreludeBase.TList<Diagnostic.TDiagnostic> v2056$17474 = Diagnostic.compileAndGetDiagnostics(
                  arg$1
                ).apply(arg$17525).call();
            final Func.U<RealWorld, java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>> v2057$17475 =
            TArrayList.<org.eclipse.lsp4j.Diagnostic, RealWorld>fromFregeList(
                  Thunk.<PreludeBase.TList<org.eclipse.lsp4j.Diagnostic>>shared(
                        (Lazy<PreludeBase.TList<org.eclipse.lsp4j.Diagnostic>>)(() -> PreludeMonad.IFunctor_$lbrack$rbrack.<
                              Diagnostic.TDiagnostic, org.eclipse.lsp4j.Diagnostic
                            >fmap(
                                  (Func.U<Diagnostic.TDiagnostic, org.eclipse.lsp4j.Diagnostic>)((
                                    final Lazy<Diagnostic.TDiagnostic> η$17527
                                  ) -> Thunk.<org.eclipse.lsp4j.Diagnostic>shared(
                                            (Lazy<org.eclipse.lsp4j.Diagnostic>)(() -> TDiagnosticLSP.fromDiagnostic(
                                                      η$17527.call()
                                                    ))
                                          )),
                                  v2056$17474
                                ))
                      )
                );
            return Thunk.<java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>>nested(
                      (Lazy<Lazy<java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>>>)(() -> v2057$17475
                          .apply(arg$17525))
                    );
          });
}

}
