/*
  Source code is in UTF-8 encoding. The following symbols may appear, among others:
  α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω « • ¦ » ∀ ∃ ∷ … → ← ﬁ ﬂ ƒ
  If you can't read this, you're out of luck. This code was generated with the frege compiler version 3.25.84
  from /Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelspserver/LSPTypeExploration.fr
  Do not edit this file! Instead, edit the source file and recompile.
*/

package ch.fhnw.thga.fregelspserver;

import frege.run8.Func;
import frege.run8.Lazy;
import frege.run8.Thunk;
import frege.run.Kind;
import frege.run.RunTM;
import frege.runtime.Meta;
import frege.runtime.Phantom.RealWorld;
import ch.fhnw.thga.fregelspserver.CompilerHelper;
import ch.fhnw.thga.fregelspserver.FregeDiagnosticService;
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
  source="/Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelspserver/LSPTypeExploration.fr",
  time=1655242389744L, jmajor=11, jminor=-1,
  imps={
    "ch.fhnw.thga.fregelspserver.FregeDiagnosticService", "frege.Prelude", "frege.prelude.PreludeArrays",
    "frege.prelude.PreludeBase", "frege.prelude.PreludeDecimal", "frege.prelude.PreludeIO", "frege.prelude.PreludeList",
    "frege.prelude.PreludeMonad", "frege.prelude.PreludeText", "frege.java.util.Regex"
  },
  nmss={
    "FregeDiagnosticService", "Prelude", "PreludeArrays", "PreludeBase", "PreludeDecimal", "PreludeIO",
    "PreludeList", "PreludeMonad", "PreludeText", "Regexp"
  },
  symas={}, symcs={}, symis={},
  symts={
    @Meta.SymT(
      offset=1202, name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="RangeLSP"),
      typ=0, kind=3, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=1327,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="RangeLSP", member="fromRange"
          ),
          stri="s(s)", sig=2, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=1271,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="RangeLSP", member="new"
          ),
          stri="s(ss)", sig=4, nativ="new", pur=true, depth=2, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.Range"
    ),
    @Meta.SymT(
      offset=979, name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="PositionLSP"),
      typ=3, kind=3, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=1106,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="PositionLSP", member="fromPosition"
          ),
          stri="s(s)", sig=6, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=1054,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="PositionLSP", member="new"
          ),
          stri="s(ss)", sig=8, nativ="new", pur=true, depth=2, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.Position"
    ),
    @Meta.SymT(
      offset=206,
      name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticSeverityLSP"),
      typ=9, kind=3, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=405,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticSeverityLSP",
            member="warning"
          ),
          stri="s", sig=9, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Warning", pur=true,
          depth=0, rkind=9
        ),
        @Meta.SymV(
          offset=613,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticSeverityLSP", member="hint"
          ),
          stri="s", sig=9, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Hint", pur=true, depth=0,
          rkind=9
        ),
        @Meta.SymV(
          offset=301,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticSeverityLSP", member="error"
          ),
          stri="s", sig=9, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Error", pur=true, depth=0,
          rkind=9
        ),
        @Meta.SymV(
          offset=706,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticSeverityLSP",
            member="fromCompilerSeverity"
          ),
          stri="s(s)", sig=11, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=509,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticSeverityLSP",
            member="information"
          ),
          stri="s", sig=9, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Information", pur=true,
          depth=0, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.DiagnosticSeverity"
    ),
    @Meta.SymT(
      offset=1484,
      name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticLSP"), typ=12,
      kind=3, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=1646,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticLSP", member="fromDiagnostic"
          ),
          stri="s(s)", sig=14, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=1563,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticLSP", member="new"
          ),
          stri="s(ssss)", sig=16, nativ="new", pur=true, depth=4, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.Diagnostic"
    ),
    @Meta.SymT(
      offset=1890, name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="ArrayList"),
      typ=17, kind=28, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=2004,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="ArrayList", member="new"
          ),
          stri="s(s)", sig=19, nativ="new", depth=1, rkind=9
        ),
        @Meta.SymV(
          offset=1946,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="ArrayList", member="add"
          ),
          stri="s(ss)", sig=22, nativ="add", depth=2, rkind=9
        ),
        @Meta.SymV(
          offset=2046,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="ArrayList", member="fromFregeList"
          ),
          stri="s(u)", sig=24, depth=1, rkind=13
        )
      },
      nativ="java.util.ArrayList", gargs={14}
    )
  },
  symvs={
    @Meta.SymV(
      offset=2310,
      name=@Meta.QName(pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="compileAndGetDiagnosticsLSP"),
      stri="s(u)", sig=25, depth=1, rkind=13
    )
  },
  symls={},
  taus={
    @Meta.Tau(
      kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="RangeLSP")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.FregeDiagnosticService", base="Range")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="PositionLSP")}
    ),
    @Meta.Tau(kind=9),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.FregeDiagnosticService", base="Position")}
    ),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Int")}),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticSeverityLSP")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.FregeDiagnosticService", base="DiagnosticSeverity")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="DiagnosticLSP")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.FregeDiagnosticService", base="Diagnostic")}
    ),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="StringJ")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Char")}),
    @Meta.Tau(kind=0, suba=10, subb=11),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelspserver.LSPTypeExploration", base="ArrayList")}
    ),
    @Meta.Tau(suba=3, tvar="a"), @Meta.Tau(kind=0, suba=13, subb=14),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="()")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="ST")}),
    @Meta.Tau(suba=3, tvar="s"), @Meta.Tau(kind=0, suba=17, subb=18),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeIO", base="Mutable")}),
    @Meta.Tau(kind=0, suba=20, subb=18), @Meta.Tau(kind=0, suba=21, subb=15), @Meta.Tau(kind=0, suba=19, subb=22),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Bool")}),
    @Meta.Tau(kind=0, suba=19, subb=24),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="[]")}),
    @Meta.Tau(kind=0, suba=26, subb=14), @Meta.Tau(kind=8, suba=3, subb=3),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="RealWorld")}),
    @Meta.Tau(kind=0, suba=17, subb=29), @Meta.Tau(kind=0, suba=20, subb=29), @Meta.Tau(kind=0, suba=13, subb=8),
    @Meta.Tau(kind=0, suba=31, subb=32), @Meta.Tau(kind=0, suba=30, subb=33)
  },
  rhos={
    @Meta.Rho(rhofun=false, rhotau=0), @Meta.Rho(rhofun=false, rhotau=1), @Meta.Rho(sigma=1, rhotau=0),
    @Meta.Rho(rhofun=false, rhotau=2), @Meta.Rho(sigma=3, rhotau=0), @Meta.Rho(sigma=3, rhotau=4),
    @Meta.Rho(rhofun=false, rhotau=4), @Meta.Rho(sigma=5, rhotau=3), @Meta.Rho(rhofun=false, rhotau=5),
    @Meta.Rho(sigma=7, rhotau=3), @Meta.Rho(sigma=7, rhotau=9), @Meta.Rho(rhofun=false, rhotau=6),
    @Meta.Rho(rhofun=false, rhotau=7), @Meta.Rho(sigma=10, rhotau=11), @Meta.Rho(rhofun=false, rhotau=8),
    @Meta.Rho(rhofun=false, rhotau=9), @Meta.Rho(sigma=13, rhotau=14), @Meta.Rho(rhofun=false, rhotau=12),
    @Meta.Rho(sigma=15, rhotau=14), @Meta.Rho(sigma=9, rhotau=18), @Meta.Rho(sigma=15, rhotau=19),
    @Meta.Rho(sigma=0, rhotau=20), @Meta.Rho(rhofun=false, rhotau=15), @Meta.Rho(rhofun=false, rhotau=16),
    @Meta.Rho(rhofun=false, rhotau=23), @Meta.Rho(sigma=18, rhotau=24), @Meta.Rho(rhofun=false, rhotau=22),
    @Meta.Rho(rhofun=false, rhotau=14), @Meta.Rho(rhofun=false, rhotau=25), @Meta.Rho(sigma=21, rhotau=28),
    @Meta.Rho(sigma=20, rhotau=29), @Meta.Rho(rhofun=false, rhotau=27), @Meta.Rho(sigma=23, rhotau=24),
    @Meta.Rho(rhofun=false, rhotau=34), @Meta.Rho(sigma=15, rhotau=33)
  },
  sigmas={
    @Meta.Sigma(rho=0), @Meta.Sigma(rho=1), @Meta.Sigma(rho=2), @Meta.Sigma(rho=3), @Meta.Sigma(rho=5),
    @Meta.Sigma(rho=6), @Meta.Sigma(rho=7), @Meta.Sigma(rho=8), @Meta.Sigma(rho=10), @Meta.Sigma(rho=11),
    @Meta.Sigma(rho=12), @Meta.Sigma(rho=13), @Meta.Sigma(rho=14), @Meta.Sigma(rho=15), @Meta.Sigma(rho=16),
    @Meta.Sigma(rho=17), @Meta.Sigma(rho=21), @Meta.Sigma(bound={"a"}, kinds={3}, rho=22), @Meta.Sigma(rho=23),
    @Meta.Sigma(bound={"a", "s"}, kinds={3, 3}, rho=25), @Meta.Sigma(rho=26), @Meta.Sigma(rho=27),
    @Meta.Sigma(bound={"a", "s"}, kinds={3, 3}, rho=30), @Meta.Sigma(rho=31),
    @Meta.Sigma(bound={"a", "s"}, kinds={3, 3}, rho=32), @Meta.Sigma(rho=34)
  },
  exprs={@Meta.Expr()}
)
final public class LSPTypeExploration  {
  



final public static class TRangeLSP  {
  final public static org.eclipse.lsp4j.Range fromRange(final FregeDiagnosticService.TRange arg$1) {
    return new org.eclipse.lsp4j.Range(
          TPositionLSP.fromPosition(FregeDiagnosticService.TRange.start(arg$1)),
          TPositionLSP.fromPosition(FregeDiagnosticService.TRange.end(arg$1))
        );
  }
}
final public static class TPositionLSP  {
  final public static org.eclipse.lsp4j.Position fromPosition(final FregeDiagnosticService.TPosition arg$1) {
    return new org.eclipse.lsp4j.Position(
          FregeDiagnosticService.TPosition.line(arg$1), FregeDiagnosticService.TPosition.character(arg$1)
        );
  }
}
final public static class TDiagnosticSeverityLSP  {
  final public static org.eclipse.lsp4j.DiagnosticSeverity fromCompilerSeverity(final short arg$1) {
    if (arg$1 == FregeDiagnosticService.TDiagnosticSeverity.HINT) {
      return org.eclipse.lsp4j.DiagnosticSeverity.Hint;
    }
    if (arg$1 == FregeDiagnosticService.TDiagnosticSeverity.WARNING) {
      return org.eclipse.lsp4j.DiagnosticSeverity.Warning;
    }
    if (arg$1 == FregeDiagnosticService.TDiagnosticSeverity.ERROR) {
      return org.eclipse.lsp4j.DiagnosticSeverity.Error;
    }
    assert arg$1 == FregeDiagnosticService.TDiagnosticSeverity.INFORMATION;
    return org.eclipse.lsp4j.DiagnosticSeverity.Information;
  }
}
final public static class TDiagnosticLSP  {
  final public static org.eclipse.lsp4j.Diagnostic fromDiagnostic(final FregeDiagnosticService.TDiagnostic arg$1) {
    return new org.eclipse.lsp4j.Diagnostic(
          TRangeLSP.fromRange(FregeDiagnosticService.TDiagnostic.range(arg$1)),
          FregeDiagnosticService.TDiagnostic.message(arg$1),
          TDiagnosticSeverityLSP.fromCompilerSeverity(FregeDiagnosticService.TDiagnostic.severity(arg$1)),
          FregeDiagnosticService.TDiagnostic.source(arg$1)
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
    return (Func.U<𝓢, java.util.ArrayList<𝓐>>)((final Lazy<𝓢> arg$17528) -> {
              final java.util.ArrayList<𝓐> v2056$17512 = TArrayList.<𝓐, 𝓢>$new(PreludeBase.TUnit.Unit)
              .apply(arg$17528).call();
              final Func.U<𝓢, java.util.ArrayList<𝓐>> v2057$17513 = LSPTypeExploration.<
                𝓐, 𝓢
              >go(arg$1.call(), Thunk.<java.util.ArrayList<𝓐>>lazy(v2056$17512));
              return Thunk.<java.util.ArrayList<𝓐>>nested(
                        (Lazy<Lazy<java.util.ArrayList<𝓐>>>)(() -> v2057$17513.apply(
                                  arg$17528
                                ))
                      );
            });
  }
}
final public static <𝓐, 𝓢> Func.U<𝓢, java.util.ArrayList<𝓐>> go(
  final PreludeBase.TList<𝓐> arg$1, final Lazy<java.util.ArrayList<𝓐>> arg$2
) {
  final PreludeBase.TList.DCons<𝓐> $17531 = arg$1.asCons();
  if ($17531 != null) {
    final 𝓐 µ$$17372 = $17531.mem1.call();
    return (Func.U<𝓢, java.util.ArrayList<𝓐>>)((final Lazy<𝓢> arg$17533) -> {
              final boolean v4796$17466 = (boolean)TArrayList.<𝓐, 𝓢>add(arg$2.call(), µ$$17372)
              .apply(arg$17533).call();
              final Func.U<𝓢, java.util.ArrayList<𝓐>> v4797$17467 = Thunk.<Func.U<𝓢, java.util.ArrayList<𝓐>>>shared(
                    (Lazy<Func.U<𝓢, java.util.ArrayList<𝓐>>>)(() -> LSPTypeExploration.<
                          𝓐, 𝓢
                        >go($17531.mem2.call(), arg$2))
                  ).call();
              return Thunk.<java.util.ArrayList<𝓐>>nested(
                        (Lazy<Lazy<java.util.ArrayList<𝓐>>>)(() -> v4797$17467.apply(
                                  arg$17533
                                ))
                      );
            });
  }
  final PreludeBase.TList.DList<𝓐> $17536 = arg$1.asList();
  assert $17536 != null;
  return PreludeMonad.IMonad_ST.<𝓢, java.util.ArrayList<𝓐>>pure(arg$2);
}
final public static Func.U<RealWorld, java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>> compileAndGetDiagnosticsLSP(
  final Lazy<String/*<Character>*/> arg$1
) {
  return (Func.U<RealWorld, java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>>)((final Lazy<RealWorld> arg$17537) -> {
            final PreludeBase.TList<FregeDiagnosticService.TDiagnostic> v2056$17490 =
            FregeDiagnosticService.compileAndGetDiagnostics(arg$1).apply(arg$17537).call();
            final Func.U<RealWorld, java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>> v2057$17491 =
            TArrayList.<org.eclipse.lsp4j.Diagnostic, RealWorld>fromFregeList(
                  Thunk.<PreludeBase.TList<org.eclipse.lsp4j.Diagnostic>>shared(
                        (Lazy<PreludeBase.TList<org.eclipse.lsp4j.Diagnostic>>)(() -> PreludeMonad.IFunctor_$lbrack$rbrack.<
                              FregeDiagnosticService.TDiagnostic, org.eclipse.lsp4j.Diagnostic
                            >fmap(
                                  (Func.U<FregeDiagnosticService.TDiagnostic, org.eclipse.lsp4j.Diagnostic>)((
                                    final Lazy<FregeDiagnosticService.TDiagnostic> η$17539
                                  ) -> Thunk.<org.eclipse.lsp4j.Diagnostic>shared(
                                            (Lazy<org.eclipse.lsp4j.Diagnostic>)(() -> TDiagnosticLSP.fromDiagnostic(
                                                      η$17539.call()
                                                    ))
                                          )),
                                  v2056$17490
                                ))
                      )
                );
            return Thunk.<java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>>nested(
                      (Lazy<Lazy<java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>>>)(() -> v2057$17491
                          .apply(arg$17537))
                    );
          });
}

}
