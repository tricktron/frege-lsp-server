/*
  Source code is in UTF-8 encoding. The following symbols may appear, among others:
  α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω « • ¦ » ∀ ∃ ∷ … → ← ﬁ ﬂ ƒ
  If you can't read this, you're out of luck. This code was generated with the frege compiler version 3.25.84
  from
  /Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/diagnostic/DiagnosticLSP.fr
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
import ch.fhnw.thga.fregelanguageserver.compile.CompileGlobal;
import ch.fhnw.thga.fregelanguageserver.compile.CompileNormalMode;
import ch.fhnw.thga.fregelanguageserver.compile.CompileOptions;
import ch.fhnw.thga.fregelanguageserver.diagnostic.Diagnostic;
import ch.fhnw.thga.fregelanguageserver.types.Generics;
import ch.fhnw.thga.fregelanguageserver.types.Position;
import ch.fhnw.thga.fregelanguageserver.types.Range;
import ch.fhnw.thga.fregelanguageserver.types.lsp.PositionLSP;
import ch.fhnw.thga.fregelanguageserver.types.lsp.RangeLSP;
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
  source="/Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/diagnostic/DiagnosticLSP.fr",
  time=1659724144364L, jmajor=11, jminor=-1,
  imps={
    "ch.fhnw.thga.fregelanguageserver.diagnostic.Diagnostic", "ch.fhnw.thga.fregelanguageserver.types.Generics",
    "frege.compiler.types.Global", "frege.Prelude", "frege.prelude.PreludeArrays", "frege.prelude.PreludeBase",
    "frege.prelude.PreludeDecimal", "frege.prelude.PreludeIO", "frege.prelude.PreludeList",
    "frege.prelude.PreludeMonad", "frege.prelude.PreludeText", "ch.fhnw.thga.fregelanguageserver.types.lsp.RangeLSP",
    "frege.java.util.Regex"
  },
  nmss={
    "Diagnostic", "Generics", "Global", "Prelude", "PreludeArrays", "PreludeBase", "PreludeDecimal",
    "PreludeIO", "PreludeList", "PreludeMonad", "PreludeText", "RangeLSP", "Regexp"
  },
  symas={}, symcs={}, symis={},
  symts={
    @Meta.SymT(
      offset=365,
      name=@Meta.QName(
        kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticSeverityLSP"
      ),
      typ=0, kind=2, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=564,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticSeverityLSP",
            member="warning"
          ),
          stri="s", sig=0, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Warning", pur=true,
          depth=0, rkind=9
        ),
        @Meta.SymV(
          offset=772,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticSeverityLSP",
            member="hint"
          ),
          stri="s", sig=0, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Hint", pur=true, depth=0,
          rkind=9
        ),
        @Meta.SymV(
          offset=460,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticSeverityLSP",
            member="error"
          ),
          stri="s", sig=0, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Error", pur=true, depth=0,
          rkind=9
        ),
        @Meta.SymV(
          offset=865,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticSeverityLSP",
            member="fromCompilerSeverity"
          ),
          stri="s(s)", sig=2, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=668,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticSeverityLSP",
            member="information"
          ),
          stri="s", sig=0, nativ="org.eclipse.lsp4j.DiagnosticSeverity.Information", pur=true,
          depth=0, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.DiagnosticSeverity"
    ),
    @Meta.SymT(
      offset=1145,
      name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticLSP"),
      typ=3, kind=2, cons={}, lnks={},
      funs={
        @Meta.SymV(
          offset=1307,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticLSP",
            member="fromDiagnostic"
          ),
          stri="s(s(ssss))", sig=5, depth=1, rkind=13
        ),
        @Meta.SymV(
          offset=1224,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticLSP",
            member="new"
          ),
          stri="s(ssss)", sig=8, nativ="new", pur=true, depth=4, rkind=9
        )
      },
      pur=true, nativ="org.eclipse.lsp4j.Diagnostic"
    )
  },
  symvs={
    @Meta.SymV(
      offset=1835, name=@Meta.QName(pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="main"),
      stri="u", sig=9, depth=0, rkind=8
    ),
    @Meta.SymV(
      offset=1583,
      name=@Meta.QName(pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="getDiagnosticsLSP"),
      stri="s(u)", sig=11, depth=1, rkind=13
    )
  },
  symls={},
  taus={
    @Meta.Tau(
      kind=2, suba=0,
      tcon={
        @Meta.QName(
          kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticSeverityLSP"
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
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.DiagnosticLSP", base="DiagnosticLSP")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.diagnostic.Diagnostic", base="Diagnostic")}
    ),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.lsp.RangeLSP", base="RangeLSP")}
    ),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="StringJ")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Char")}),
    @Meta.Tau(kind=0, suba=6, subb=7),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="ST")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="RealWorld")}),
    @Meta.Tau(kind=0, suba=9, subb=10),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="()")}),
    @Meta.Tau(kind=0, suba=11, subb=12),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.compiler.types.Global", base="Global")}),
    @Meta.Tau(suba=2, tvar="s"), @Meta.Tau(kind=0, suba=9, subb=15),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeIO", base="Mutable")}),
    @Meta.Tau(kind=0, suba=17, subb=15),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.Generics", base="ArrayList")}
    ),
    @Meta.Tau(kind=0, suba=19, subb=3), @Meta.Tau(kind=0, suba=18, subb=20), @Meta.Tau(kind=0, suba=16, subb=21)
  },
  rhos={
    @Meta.Rho(rhofun=false, rhotau=0), @Meta.Rho(rhofun=false, rhotau=1), @Meta.Rho(sigma=1, rhotau=0),
    @Meta.Rho(rhofun=false, rhotau=3), @Meta.Rho(rhofun=false, rhotau=4), @Meta.Rho(sigma=4, rhotau=3),
    @Meta.Rho(rhofun=false, rhotau=5), @Meta.Rho(rhofun=false, rhotau=8), @Meta.Rho(sigma=7, rhotau=3),
    @Meta.Rho(sigma=0, rhotau=8), @Meta.Rho(sigma=7, rhotau=9), @Meta.Rho(sigma=6, rhotau=10),
    @Meta.Rho(rhofun=false, rhotau=13), @Meta.Rho(rhofun=false, rhotau=14), @Meta.Rho(rhofun=false, rhotau=22),
    @Meta.Rho(sigma=10, rhotau=14)
  },
  sigmas={
    @Meta.Sigma(rho=0), @Meta.Sigma(rho=1), @Meta.Sigma(rho=2), @Meta.Sigma(rho=3), @Meta.Sigma(rho=4),
    @Meta.Sigma(rho=5), @Meta.Sigma(rho=6), @Meta.Sigma(rho=7), @Meta.Sigma(rho=11), @Meta.Sigma(rho=12),
    @Meta.Sigma(rho=13), @Meta.Sigma(bound={"s"}, kinds={2}, rho=15)
  },
  exprs={@Meta.Expr()}
)
final public class DiagnosticLSP  {
  



final public static class TDiagnosticSeverityLSP  {
  final public static org.eclipse.lsp4j.DiagnosticSeverity fromCompilerSeverity(final short arg$1) {
    if (arg$1 == Diagnostic.TDiagnosticSeverity.HINT) {
      return org.eclipse.lsp4j.DiagnosticSeverity.Information;
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
    final String/*<Character>*/ message$17737 = arg$1.mem$message.call();
    final String/*<Character>*/ source$17736 = arg$1.mem$source.call();
    final short severity$17735 = (short)arg$1.mem$severity.call();
    final Range.TRange range$17734 = arg$1.mem$range.call();
    return new org.eclipse.lsp4j.Diagnostic(
          RangeLSP.TRangeLSP.fromRange(range$17734), message$17737, TDiagnosticSeverityLSP.fromCompilerSeverity(severity$17735),
          source$17736
        );
  }
}
final public static Lazy<Func.U<RealWorld, Short>> $main = Thunk.<Func.U<RealWorld, Short>>shared(
      (Lazy<Func.U<RealWorld, Short>>)(() -> {
            return Prelude.<String/*<Character>*/>println(PreludeText.IShow_String.it, "hello DiagnosticLSP");
          })
    );
final public static <𝓢> Func.U<𝓢, java.util.ArrayList<org.eclipse.lsp4j.Diagnostic>> getDiagnosticsLSP(
  final Lazy<Global.TGlobal> arg$1
) {
  return Generics.TArrayList.<org.eclipse.lsp4j.Diagnostic, 𝓢>fromFregeList(
            Thunk.<PreludeBase.TList<org.eclipse.lsp4j.Diagnostic>>shared(
                  (Lazy<PreludeBase.TList<org.eclipse.lsp4j.Diagnostic>>)(() -> PreludeMonad.IFunctor_$lbrack$rbrack.<
                        Diagnostic.TDiagnostic, org.eclipse.lsp4j.Diagnostic
                      >fmap(
                            (Func.U<Diagnostic.TDiagnostic, org.eclipse.lsp4j.Diagnostic>)((
                              final Lazy<Diagnostic.TDiagnostic> η$17765
                            ) -> Thunk.<org.eclipse.lsp4j.Diagnostic>shared(
                                      (Lazy<org.eclipse.lsp4j.Diagnostic>)(() -> TDiagnosticLSP.fromDiagnostic(
                                                η$17765.call()
                                              ))
                                    )),
                            Diagnostic.getDiagnostics(arg$1)
                          ))
                )
          );
}

  public static void main(final java.lang.String[] argv) {
    try {
      frege.run.RunTM.argv = argv;
      
        PreludeBase.TST.<Short>performUnsafe($main
               .call()
          ).call();
      frege.runtime.Runtime.stdout.get().close();
      frege.runtime.Runtime.stderr.get().close();

    } finally { frege.run.Concurrent.shutDownIfExists(); }
  }
}
