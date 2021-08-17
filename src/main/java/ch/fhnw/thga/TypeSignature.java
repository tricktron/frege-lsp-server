/*
  Source code is in UTF-8 encoding. The following symbols may appear, among others:
  α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω « • ¦ » ∀ ∃ ∷ … → ← ﬁ ﬂ ƒ
  If you can't read this, you're out of luck. This code was generated with the frege compiler version 3.25.84
  from /Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/TypeSignature.fr
  Do not edit this file! Instead, edit the source file and recompile.
*/

package ch.fhnw.thga;

import frege.run8.Func;
import frege.run8.Lazy;
import frege.run8.Thunk;
import frege.run.Kind;
import frege.run.RunTM;
import frege.runtime.Meta;
import frege.runtime.Phantom.RealWorld;
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
import frege.control.Arrow;
import frege.control.Category;
import frege.control.CombineIn;
import frege.control.Concurrent;
import frege.control.First;
import frege.control.Second;
import frege.control.Semigroupoid;
import frege.control.Tensor;
import frege.control.arrow.Kleisli;
import frege.control.monad.Reader;
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
import frege.interpreter.FregeInterpreter;
import frege.java.Awt;
import frege.java.IO;
import frege.java.Lang;
import frege.java.Net;
import frege.java.Swing;
import frege.java.lang.Reflect;
import frege.java.swing.GroupLayout;
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
import frege.repl.FregeRepl;
import frege.repl.Gui;
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
  source="/Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/TypeSignature.fr",
  time=1629217424469L, jmajor=11, jminor=-1,
  imps={
    "frege.interpreter.FregeInterpreter", "frege.repl.FregeRepl", "frege.control.monad.trans.MonadIO", "frege.Prelude",
    "frege.prelude.PreludeArrays", "frege.prelude.PreludeBase", "frege.prelude.PreludeDecimal",
    "frege.prelude.PreludeIO", "frege.prelude.PreludeList", "frege.prelude.PreludeMonad", "frege.prelude.PreludeText",
    "frege.java.util.Regex"
  },
  nmss={
    "FregeInterpreter", "FregeRepl", "MonadIO", "Prelude", "PreludeArrays", "PreludeBase", "PreludeDecimal",
    "PreludeIO", "PreludeList", "PreludeMonad", "PreludeText", "Regexp"
  },
  symas={}, symcs={}, symis={}, symts={},
  symvs={
    @Meta.SymV(
      offset=390, name=@Meta.QName(pack="ch.fhnw.thga.TypeSignature", base="getFunctionTypeSignature"), stri="s(uu)",
      sig=2, depth=2, rkind=12
    ),
    @Meta.SymV(
      offset=534, name=@Meta.QName(pack="ch.fhnw.thga.TypeSignature", base="evalType"), stri="s(u)",
      sig=3, depth=1, rkind=13
    ),
    @Meta.SymV(
      offset=209, name=@Meta.QName(pack="ch.fhnw.thga.TypeSignature", base="evalFregeFile"), stri="s(u)",
      sig=4, depth=1, rkind=13
    )
  },
  symls={},
  taus={
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="StringJ")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Char")}),
    @Meta.Tau(kind=0, suba=0, subb=1),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.repl.FregeRepl", base="ReplEnv")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="ST")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="RealWorld")}),
    @Meta.Tau(kind=0, suba=4, subb=5),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="(,)")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Maybe")}),
    @Meta.Tau(kind=0, suba=8, subb=2), @Meta.Tau(kind=0, suba=7, subb=9), @Meta.Tau(kind=0, suba=10, subb=3),
    @Meta.Tau(kind=0, suba=6, subb=11),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.repl.FregeRepl", base="Repl")}),
    @Meta.Tau(kind=0, suba=13, subb=9),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.repl.FregeRepl", base="ReplResult")}),
    @Meta.Tau(kind=0, suba=7, subb=15), @Meta.Tau(kind=0, suba=16, subb=3), @Meta.Tau(kind=0, suba=6, subb=17)
  },
  rhos={
    @Meta.Rho(rhofun=false, rhotau=2), @Meta.Rho(rhofun=false, rhotau=3), @Meta.Rho(rhofun=false, rhotau=12),
    @Meta.Rho(sigma=1, rhotau=2), @Meta.Rho(sigma=0, rhotau=3), @Meta.Rho(rhofun=false, rhotau=14),
    @Meta.Rho(sigma=0, rhotau=5), @Meta.Rho(rhofun=false, rhotau=18), @Meta.Rho(sigma=0, rhotau=7)
  },
  sigmas={@Meta.Sigma(rho=0), @Meta.Sigma(rho=1), @Meta.Sigma(rho=4), @Meta.Sigma(rho=6), @Meta.Sigma(rho=8)},
  exprs={@Meta.Expr()}
)
final public class TypeSignature  {
  




final public static FregeRepl.TRepl<PreludeBase.TMaybe<String/*<Character>*/>> evalType(
  final Lazy<String/*<Character>*/> arg$1
) {
  return FregeRepl.IMonad_Repl.<FregeRepl.TReplEnv, PreludeBase.TMaybe<String/*<Character>*/>>$gt$gt$eq(
            FregeRepl.TRepl.get,
            (Func.U<FregeRepl.TReplEnv, FregeRepl.TRepl<PreludeBase.TMaybe<String/*<Character>*/>>>)((
              final Lazy<FregeRepl.TReplEnv> arg$18830
            ) -> {
                  return Thunk.<FregeRepl.TRepl<PreludeBase.TMaybe<String/*<Character>*/>>>shared(
                            (Lazy<FregeRepl.TRepl<PreludeBase.TMaybe<String/*<Character>*/>>>)(() -> FregeRepl.IMonad_Repl.<
                                  PreludeBase.TEither<PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/>,
                                  PreludeBase.TMaybe<String/*<Character>*/>
                                >$gt$gt$eq(
                                      PreludeBase.<
                                        FregeRepl.TRepl<
                                          PreludeBase.TEither<
                                            PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                          >
                                        >,
                                        Func.U<
                                          RealWorld,
                                          PreludeBase.TEither<
                                            PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                          >
                                        >
                                      >$(
                                            (Func.U<
                                              Func.U<
                                                RealWorld,
                                                PreludeBase.TEither<
                                                  PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                                >
                                              >,
                                              FregeRepl.TRepl<
                                                PreludeBase.TEither<
                                                  PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                                >
                                              >
                                            >)((
                                              final Lazy<Func.U<
                                                RealWorld,
                                                PreludeBase.TEither<
                                                  PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                                >
                                              >> η$18831
                                            ) -> Thunk.<
                                                  FregeRepl.TRepl<
                                                    PreludeBase.TEither<
                                                      PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                                    >
                                                  >
                                                >shared(
                                                      (Lazy<FregeRepl.TRepl<
                                                        PreludeBase.TEither<
                                                          PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                                        >
                                                      >>)(() -> FregeRepl.IMonadIO_Repl.<
                                                            PreludeBase.TEither<
                                                              PreludeBase.TList<FregeInterpreter.TMessage>,
                                                              String/*<Character>*/
                                                            >
                                                          >liftIO(η$18831))
                                                    )),
                                            Thunk.<
                                              Func.U<
                                                RealWorld,
                                                PreludeBase.TEither<
                                                  PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                                >
                                              >
                                            >shared(
                                                  (Lazy<Func.U<
                                                    RealWorld,
                                                    PreludeBase.TEither<
                                                      PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                                    >
                                                  >>)(() -> PreludeMonad.IMonad_ST.<
                                                        RealWorld,
                                                        PreludeBase.TTuple2<
                                                          PreludeBase.TEither<
                                                            PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                                          >,
                                                          frege.interpreter.javasupport.InterpreterClassLoader
                                                        >,
                                                        PreludeBase.TEither<
                                                          PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                                        >
                                                      >fmap(
                                                            (Func.U<
                                                              PreludeBase.TTuple2<
                                                                PreludeBase.TEither<
                                                                  PreludeBase.TList<FregeInterpreter.TMessage>,
                                                                  String/*<Character>*/
                                                                >,
                                                                frege.interpreter.javasupport.InterpreterClassLoader
                                                              >,
                                                              PreludeBase.TEither<
                                                                PreludeBase.TList<FregeInterpreter.TMessage>,
                                                                String/*<Character>*/
                                                              >
                                                            >)((
                                                              final Lazy<PreludeBase.TTuple2<
                                                                PreludeBase.TEither<
                                                                  PreludeBase.TList<FregeInterpreter.TMessage>,
                                                                  String/*<Character>*/
                                                                >,
                                                                frege.interpreter.javasupport.InterpreterClassLoader
                                                              >> η$18832
                                                            ) -> Thunk.<
                                                                  PreludeBase.TEither<
                                                                    PreludeBase.TList<
                                                                      FregeInterpreter.TMessage
                                                                    >,
                                                                    String/*<Character>*/
                                                                  >
                                                                >shared(
                                                                      (Lazy<PreludeBase.TEither<
                                                                        PreludeBase.TList<
                                                                          FregeInterpreter.TMessage
                                                                        >,
                                                                        String/*<Character>*/
                                                                      >>)(() -> PreludeBase.<
                                                                            PreludeBase.TEither<
                                                                              PreludeBase.TList<
                                                                                FregeInterpreter.TMessage
                                                                              >,
                                                                              String/*<Character>*/
                                                                            >,
                                                                            frege.interpreter.javasupport.InterpreterClassLoader
                                                                          >fst(η$18832.call()))
                                                                    )),
                                                            FregeInterpreter.TInterpreter.<
                                                              PreludeBase.TEither<
                                                                PreludeBase.TList<FregeInterpreter.TMessage>,
                                                                String/*<Character>*/
                                                              >
                                                            >run(
                                                                  FregeInterpreter.typeof(
                                                                        arg$1
                                                                      ),
                                                                  FregeRepl.TReplEnv.config(
                                                                        arg$18830.call()
                                                                      ),
                                                                  Thunk.<
                                                                    frege.interpreter.javasupport.InterpreterClassLoader
                                                                  >lazy(FregeRepl.TReplEnv.state(arg$18830.call()))
                                                                )
                                                          ))
                                                )
                                          ).call(),
                                      (Func.U<
                                        PreludeBase.TEither<
                                          PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                        >,
                                        FregeRepl.TRepl<PreludeBase.TMaybe<String/*<Character>*/>>
                                      >)((
                                        final Lazy<PreludeBase.TEither<
                                          PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                        >> arg$18833
                                      ) -> {
                                            final PreludeBase.TEither<
                                              PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                            > res$18737 = arg$18833.call();
                                            final PreludeBase.TEither.DLeft<
                                              PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                            > $18835 = res$18737.asLeft();
                                            if ($18835 != null) {
                                              return Thunk.<FregeRepl.TRepl<PreludeBase.TMaybe<String/*<Character>*/>>>shared(
                                                        (Lazy<FregeRepl.TRepl<
                                                          PreludeBase.TMaybe<String/*<Character>*/>
                                                        >>)(() -> FregeRepl.IMonad_Repl.<
                                                              PreludeBase.TMaybe<String/*<Character>*/>
                                                            >pure(PreludeBase.TMaybe.DNothing.<String/*<Character>*/>mk()))
                                                      );
                                            }
                                            final PreludeBase.TEither.DRight<
                                              PreludeBase.TList<FregeInterpreter.TMessage>, String/*<Character>*/
                                            > $18836 = res$18737.asRight();
                                            assert $18836 != null;
                                            return PreludeBase.<
                                                  FregeRepl.TRepl<PreludeBase.TMaybe<String/*<Character>*/>>,
                                                  PreludeBase.TMaybe<String/*<Character>*/>
                                                >$(
                                                      (Func.U<
                                                        PreludeBase.TMaybe<String/*<Character>*/>,
                                                        FregeRepl.TRepl<PreludeBase.TMaybe<String/*<Character>*/>>
                                                      >)((
                                                        final Lazy<PreludeBase.TMaybe<
                                                          String/*<Character>*/
                                                        >> η$18837
                                                      ) -> Thunk.<
                                                            FregeRepl.TRepl<PreludeBase.TMaybe<String/*<Character>*/>>
                                                          >shared(
                                                                (Lazy<FregeRepl.TRepl<
                                                                  PreludeBase.TMaybe<String/*<Character>*/>
                                                                >>)(() -> FregeRepl.IMonad_Repl.<
                                                                      PreludeBase.TMaybe<
                                                                        String/*<Character>*/
                                                                      >
                                                                    >pure(η$18837))
                                                              )),
                                                      PreludeBase.TMaybe.DJust.<String/*<Character>*/>mk(
                                                            $18836.mem1
                                                          )
                                                    );
                                          })
                                    ))
                          );
                })
          );
}
final public static Lazy<Func.U<
  RealWorld, PreludeBase.TTuple2<PreludeBase.TMaybe<String/*<Character>*/>, FregeRepl.TReplEnv>
>> getFunctionTypeSignature(final Lazy<String/*<Character>*/> arg$1, final Lazy<FregeRepl.TReplEnv> arg$2) {
  return FregeRepl.TRepl.<PreludeBase.TMaybe<String/*<Character>*/>>run(TypeSignature.evalType(arg$1), arg$2);
}
final public static Func.U<RealWorld, PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>> evalFregeFile(
  final Lazy<String/*<Character>*/> arg$1
) {
  return PreludeBase.TST.<
        RealWorld, FregeRepl.TReplEnv, PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>
      >$gt$gt$eq(
            FregeRepl.TReplEnv.initialState.call(),
            (Func.U<FregeRepl.TReplEnv, Func.U<RealWorld, PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>>>)((
              final Lazy<FregeRepl.TReplEnv> arg$18838
            ) -> {
                  return Thunk.<Func.U<RealWorld, PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>>>shared(
                            (Lazy<Func.U<
                              RealWorld, PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>
                            >>)(() -> PreludeBase.TST.<
                                  RealWorld, PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>,
                                  PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>
                                >$gt$gt$eq(
                                      FregeRepl.TRepl.<FregeRepl.TReplResult>run(
                                            PreludeBase.<FregeRepl.TRepl<FregeRepl.TReplResult>, FregeRepl.TCommand>$(
                                                  (Func.U<FregeRepl.TCommand, FregeRepl.TRepl<FregeRepl.TReplResult>>)((
                                                    final Lazy<FregeRepl.TCommand> η$18839
                                                  ) -> Thunk.<FregeRepl.TRepl<FregeRepl.TReplResult>>nested(
                                                            (Lazy<Lazy<FregeRepl.TRepl<
                                                              FregeRepl.TReplResult
                                                            >>>)(() -> FregeRepl.eval(
                                                                      η$18839.call()
                                                                    ))
                                                          )),
                                                  FregeRepl.TCommand.DEval.mk(arg$1)
                                                ).call(),
                                            arg$18838
                                          ).call(),
                                      (Func.U<
                                        PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>,
                                        Func.U<RealWorld, PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>>
                                      >)((
                                        final Lazy<PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>> arg$18840
                                      ) -> {
                                            final PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv> $18841 =
                                            arg$18840.call();
                                            return Thunk.<
                                                  Func.U<
                                                    RealWorld, PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>
                                                  >
                                                >shared(
                                                      (Lazy<Func.U<
                                                        RealWorld,
                                                        PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>
                                                      >>)(() -> PreludeMonad.IMonad_ST.<
                                                            RealWorld,
                                                            PreludeBase.TTuple2<FregeRepl.TReplResult, FregeRepl.TReplEnv>
                                                          >pure(
                                                                PreludeBase.TTuple2.<
                                                                  FregeRepl.TReplResult, FregeRepl.TReplEnv
                                                                >mk($18841.mem1, $18841.mem2)
                                                              ))
                                                    );
                                          })
                                    ))
                          );
                })
          );
}

}
