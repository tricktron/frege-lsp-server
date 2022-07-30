/*
  Source code is in UTF-8 encoding. The following symbols may appear, among others:
  α β γ δ ε ζ η θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω « • ¦ » ∀ ∃ ∷ … → ← ﬁ ﬂ ƒ
  If you can't read this, you're out of luck. This code was generated with the frege compiler version 3.25.84
  from /Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/types/Range.fr
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
import ch.fhnw.thga.fregelanguageserver.types.Position;
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
  source="/Users/tricktron/github/master/frege-lsp-server/src/main/frege/ch/fhnw/thga/fregelanguageserver/types/Range.fr",
  time=1659200052366L, jmajor=11, jminor=-1,
  imps={
    "ch.fhnw.thga.fregelanguageserver.types.Position", "frege.Prelude", "frege.prelude.PreludeArrays",
    "frege.prelude.PreludeBase", "frege.prelude.PreludeDecimal", "frege.prelude.PreludeIO", "frege.prelude.PreludeList",
    "frege.prelude.PreludeMonad", "frege.prelude.PreludeText", "frege.java.util.Regex", "frege.compiler.types.Tokens"
  },
  nmss={
    "Position", "Prelude", "PreludeArrays", "PreludeBase", "PreludeDecimal", "PreludeIO", "PreludeList",
    "PreludeMonad", "PreludeText", "Regexp", "Tokens"
  },
  symas={}, symcs={},
  symis={
    @Meta.SymI(
      offset=424, name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range"),
      clas=@Meta.QName(kind=0, pack="frege.prelude.PreludeText", base="Show"), typ=0, lnks={},
      funs={
        @Meta.SymV(
          offset=424,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="showsPrec"
          ),
          stri="s(uss)", sig=3, depth=3, rkind=13, doc="inherited from 'Show.showsPrec'"
        ),
        @Meta.SymV(
          offset=424,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="showsub"
          ),
          stri="s(s)", sig=4, depth=1, rkind=13, doc="Function generated for derived instance."
        ),
        @Meta.SymV(
          offset=424,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="showChars"
          ),
          stri="s(s)", sig=5, depth=1, rkind=13, doc="inherited from 'Show.showChars'"
        ),
        @Meta.SymV(
          offset=424,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="display"
          ),
          stri="s(s)", sig=4, depth=1, rkind=13, doc="inherited from 'Show.display'"
        ),
        @Meta.SymV(
          offset=424,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="show"
          ),
          stri="s(s)", sig=4, depth=1, rkind=13, doc="Function generated for derived instance."
        ),
        @Meta.SymV(
          offset=424,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="showList"
          ),
          stri="s(ss)", sig=7, depth=2, rkind=13, doc="inherited from 'Show.showList'"
        )
      }
    ),
    @Meta.SymI(
      offset=408, name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Eq_Range"),
      clas=@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Eq"), typ=0, lnks={},
      funs={
        @Meta.SymV(
          offset=408,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Eq_Range", member="hashCode"
          ),
          stri="s(s)", sig=8, depth=1, rkind=13, doc="Function generated for derived instance."
        ),
        @Meta.SymV(
          offset=408,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Eq_Range", member="!="),
          stri="s(ss)", sig=9, depth=2, rkind=13, doc="inherited from 'Eq.!='", op=96
        ),
        @Meta.SymV(
          offset=408,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Eq_Range", member="=="),
          stri="s(ss)", sig=9, depth=2, rkind=13, doc="Function generated for derived instance.", op=96
        )
      }
    )
  },
  symts={
    @Meta.SymT(
      offset=168, name=@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range"), typ=0,
      kind=10,
      cons={
        @Meta.SymD(
          offset=176,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="Range"),
          cid=0, typ=11,
          fields={
            @Meta.Field(name="start", offset=188, sigma=10, strict=false),
            @Meta.Field(name="end", offset=211, sigma=10, strict=false)
          }
        )
      },
      lnks={
        @Meta.SymL(
          offset=424,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="showsub"),
          alias=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="showsub"
          )
        ),
        @Meta.SymL(
          offset=424,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="showChars"
          ),
          alias=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="showChars"
          )
        ),
        @Meta.SymL(
          offset=424,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="showList"
          ),
          alias=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="showList"
          )
        ),
        @Meta.SymL(
          offset=408,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="hashCode"
          ),
          alias=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Eq_Range", member="hashCode"
          )
        ),
        @Meta.SymL(
          offset=424,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="show"),
          alias=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="show"
          )
        ),
        @Meta.SymL(
          offset=424,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="showsPrec"
          ),
          alias=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="showsPrec"
          )
        ),
        @Meta.SymL(
          offset=424,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="display"),
          alias=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Show_Range", member="display"
          )
        ),
        @Meta.SymL(
          offset=408,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="!="),
          alias=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Eq_Range", member="!=")
        ),
        @Meta.SymL(
          offset=408,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="=="),
          alias=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Eq_Range", member="==")
        )
      },
      funs={
        @Meta.SymV(
          offset=189,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="upd$start"
          ),
          stri="s(su)", sig=12, depth=2, rkind=13, doc="update field @start@"
        ),
        @Meta.SymV(
          offset=212,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="upd$end"),
          stri="s(su)", sig=12, depth=2, rkind=13, doc="update field @end@"
        ),
        @Meta.SymV(
          offset=189,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="start"),
          stri="s(s)", sig=13, depth=1, rkind=13, doc="access field @start@"
        ),
        @Meta.SymV(
          offset=212,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="has$end"),
          stri="s(u)", sig=15, depth=1, rkind=13, doc="check if constructor has field @end@"
        ),
        @Meta.SymV(
          offset=189,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="has$start"
          ),
          stri="s(u)", sig=15, depth=1, rkind=13, doc="check if constructor has field @start@"
        ),
        @Meta.SymV(
          offset=189,
          name=@Meta.QName(
            kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="chg$start"
          ),
          stri="s(su)", sig=17, depth=2, rkind=13, doc="change field @start@"
        ),
        @Meta.SymV(
          offset=212,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="chg$end"),
          stri="s(su)", sig=17, depth=2, rkind=13, doc="change field @end@"
        ),
        @Meta.SymV(
          offset=212,
          name=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="end"),
          stri="s(s)", sig=13, depth=1, rkind=13, doc="access field @end@"
        )
      },
      prod=true
    )
  },
  symvs={
    @Meta.SymV(
      offset=249, name=@Meta.QName(pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="tokenToRange"),
      stri="s(s(uuuuuu))", sig=19, depth=1, rkind=13
    )
  },
  symls={
    @Meta.SymL(
      offset=176, name=@Meta.QName(pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range"),
      alias=@Meta.QName(kind=2, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range", member="Range")
    )
  },
  taus={
    @Meta.Tau(
      kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.Range", base="Range")}
    ),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Int")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="StringJ")}),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Char")}),
    @Meta.Tau(kind=0, suba=2, subb=3),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="[]")}),
    @Meta.Tau(kind=0, suba=5, subb=3), @Meta.Tau(kind=0, suba=5, subb=0),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="Bool")}),
    @Meta.Tau(
      kind=2, suba=0,
      tcon={@Meta.QName(kind=0, pack="ch.fhnw.thga.fregelanguageserver.types.Position", base="Position")}
    ),
    @Meta.Tau(kind=9), @Meta.Tau(suba=10, tvar="α"),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.prelude.PreludeBase", base="->")}),
    @Meta.Tau(kind=0, suba=12, subb=9), @Meta.Tau(kind=0, suba=13, subb=9),
    @Meta.Tau(kind=2, suba=0, tcon={@Meta.QName(kind=0, pack="frege.compiler.types.Tokens", base="Token")})
  },
  rhos={
    @Meta.Rho(rhofun=false, rhotau=0), @Meta.Rho(rhofun=false, rhotau=1), @Meta.Rho(rhofun=false, rhotau=4),
    @Meta.Rho(sigma=2, rhotau=2), @Meta.Rho(sigma=0, rhotau=3), @Meta.Rho(sigma=1, rhotau=4),
    @Meta.Rho(sigma=0, rhotau=2), @Meta.Rho(rhofun=false, rhotau=6), @Meta.Rho(sigma=0, rhotau=7),
    @Meta.Rho(rhofun=false, rhotau=7), @Meta.Rho(sigma=6, rhotau=3), @Meta.Rho(sigma=0, rhotau=1),
    @Meta.Rho(rhofun=false, rhotau=8), @Meta.Rho(sigma=0, rhotau=12), @Meta.Rho(sigma=0, rhotau=13),
    @Meta.Rho(rhofun=false, rhotau=9), @Meta.Rho(sigma=10, rhotau=0), @Meta.Rho(sigma=10, rhotau=16),
    @Meta.Rho(sigma=0, rhotau=16), @Meta.Rho(sigma=0, rhotau=15), @Meta.Rho(rhofun=false, rhotau=11),
    @Meta.Rho(sigma=14, rhotau=12), @Meta.Rho(rhofun=false, rhotau=14), @Meta.Rho(sigma=16, rhotau=0),
    @Meta.Rho(sigma=0, rhotau=23), @Meta.Rho(rhofun=false, rhotau=15), @Meta.Rho(sigma=18, rhotau=0)
  },
  sigmas={
    @Meta.Sigma(rho=0), @Meta.Sigma(rho=1), @Meta.Sigma(rho=2), @Meta.Sigma(rho=5), @Meta.Sigma(rho=6),
    @Meta.Sigma(rho=8), @Meta.Sigma(rho=9), @Meta.Sigma(rho=10), @Meta.Sigma(rho=11), @Meta.Sigma(rho=14),
    @Meta.Sigma(rho=15), @Meta.Sigma(rho=17), @Meta.Sigma(rho=18), @Meta.Sigma(rho=19), @Meta.Sigma(rho=20),
    @Meta.Sigma(bound={"α"}, kinds={10}, rho=21), @Meta.Sigma(rho=22), @Meta.Sigma(rho=24), @Meta.Sigma(rho=25),
    @Meta.Sigma(rho=26)
  },
  exprs={@Meta.Expr()}
)
final public class Range  {
  


final public static class IShow_Range implements PreludeText.CShow<TRange> {
  public IShow_Range() {}
  final public static IShow_Range it = new IShow_Range();
  @Override final public String/*<Character>*/ ƒshowsPrec(
    final Lazy<Integer> arg$1, final Lazy<TRange> arg$2, final Lazy<String/*<Character>*/> arg$3
  ) {
    return IShow_Range.showsPrec(arg$1, arg$2.call(), arg$3.call());
  }
  @Override final public String/*<Character>*/ ƒshowsub(final Lazy<TRange> arg$1) {
    return IShow_Range.showsub(arg$1.call());
  }
  @Override final public PreludeBase.TList<Character> ƒshowChars(final Lazy<TRange> arg$1) {
    return IShow_Range.showChars(arg$1.call());
  }
  @Override final public String/*<Character>*/ ƒdisplay(final Lazy<TRange> arg$1) {
    return IShow_Range.display(arg$1.call());
  }
  @Override final public String/*<Character>*/ ƒshow(final Lazy<TRange> arg$1) {
    return IShow_Range.show(arg$1.call());
  }
  @Override final public String/*<Character>*/ ƒshowList(
    final Lazy<PreludeBase.TList<TRange>> arg$1, final Lazy<String/*<Character>*/> arg$2
  ) {
    return IShow_Range.showList(arg$1.call(), arg$2.call());
  }
  final public static String/*<Character>*/ showsPrec(
    final Lazy<Integer> arg$1, final TRange arg$2, final String/*<Character>*/ arg$3
  ) {
    return IShow_Range.show(arg$2) + arg$3;
  }
  final public static String/*<Character>*/ showsub(final TRange arg$1) {
    final Position.TPosition a2$8106 = arg$1.mem$end.call();
    final Position.TPosition a1$8105 = arg$1.mem$start.call();
    return ("(" + (((("Range" + " ") + Position.IShow_Position.showsub(a1$8105)) + " ") + Position.IShow_Position.showsub(
              a2$8106
            ))) + ")";
  }
  final public static PreludeBase.TList<Character> showChars(final TRange arg$1) {
    return PreludeList.IListView_StringJ.<Character>toList(IShow_Range.show(arg$1));
  }
  final public static String/*<Character>*/ display(final TRange arg$1) {
    return IShow_Range.show(arg$1);
  }
  final public static String/*<Character>*/ show(final TRange arg$1) {
    final Position.TPosition a2$8103 = arg$1.mem$end.call();
    final Position.TPosition a1$8102 = arg$1.mem$start.call();
    return ((("Range" + " ") + Position.IShow_Position.showsub(a1$8102)) + " ") + Position.IShow_Position.showsub(
              a2$8103
            );
  }
  final public static String/*<Character>*/ showList(final PreludeBase.TList<TRange> arg$1, final String/*<Character>*/ arg$2) {
    return "[" + (PreludeText.joined(
              Thunk.<String/*<Character>*/>lazy(", "),
              PreludeList.<String/*<Character>*/, TRange>map(
                    (Func.U<TRange, String/*<Character>*/>)((final Lazy<TRange> η$8330) -> Thunk.<
                          String/*<Character>*/
                        >shared((Lazy<String/*<Character>*/>)(() -> IShow_Range.show(η$8330.call())))),
                    arg$1
                  )
            ).call() + ("]" + arg$2));
  }
}
final public static class IEq_Range implements PreludeBase.CEq<TRange> {
  public IEq_Range() {}
  final public static IEq_Range it = new IEq_Range();
  @Override final public int ƒhashCode(final Lazy<TRange> arg$1) {
    return IEq_Range.hashCode(arg$1.call());
  }
  @Override final public boolean ƒ$excl$eq(final Lazy<TRange> arg$1, final Lazy<TRange> arg$2) {
    return IEq_Range.$excl$eq(arg$1.call(), arg$2.call());
  }
  @Override final public boolean ƒ$eq$eq(final Lazy<TRange> arg$1, final Lazy<TRange> arg$2) {
    return IEq_Range.$eq$eq(arg$1.call(), arg$2.call());
  }
  final public static int hashCode(final TRange arg$1) {
    final Position.TPosition a2$8100 = arg$1.mem$end.call();
    final Position.TPosition a1$8099 = arg$1.mem$start.call();
    return (31 * ((31 * ((31 * 1) + RunTM.constructor(arg$1))) + Position.IEq_Position.hashCode(
              a1$8099
            ))) + Position.IEq_Position.hashCode(a2$8100);
  }
  final public static boolean $excl$eq(final TRange arg$1, final TRange arg$2) {
    if (IEq_Range.$eq$eq(arg$1, arg$2)) {
      return false;
    }
    else {
      return true;
    }
  }
  final public static boolean $eq$eq(final TRange arg$1, final TRange arg$2) {
    final Position.TPosition µ$$8136 = arg$1.mem$end.call();
    final Position.TPosition µ$$8135 = arg$1.mem$start.call();
    final Position.TPosition µ$$8138 = arg$2.mem$end.call();
    final Position.TPosition µ$$8137 = arg$2.mem$start.call();
    return Position.IEq_Position.$eq$eq(µ$$8135, µ$$8137) && Position.IEq_Position.$eq$eq(
              µ$$8136, µ$$8138
            );
  }
}
final public static class TRange implements frege.runtime.Value, Lazy<TRange> {
  private TRange(final Lazy<Position.TPosition> arg$1, final Lazy<Position.TPosition> arg$2) {
    mem$start = Thunk.<Position.TPosition>shared(arg$1);
    mem$end = Thunk.<Position.TPosition>shared(arg$2);
  }
  final public int constructor() {
    return 0;
  }
  final public static TRange mk(final Lazy<Position.TPosition> arg$1, final Lazy<Position.TPosition> arg$2) {
    return new TRange(arg$1, arg$2);
  }
  final public Lazy<Position.TPosition> mem$start  ;
  final public Lazy<Position.TPosition> mem$end  ;
  final public TRange call() {
    return this;
  }
  final public boolean isShared() {
    return true;
  }
  final public Thunk<TRange> asThunk() {
    return null;
  }
  @SuppressWarnings("unchecked") final public TRange simsalabim() {
    return (TRange)this;
  }
  final public static TRange upd$start(final TRange arg$1, final Lazy<Position.TPosition> arg$2) {
    return TRange.mk(arg$2, arg$1.mem$end);
  }
  final public static TRange upd$end(final TRange arg$1, final Lazy<Position.TPosition> arg$2) {
    return TRange.mk(arg$1.mem$start, arg$2);
  }
  final public static Position.TPosition start(final TRange arg$1) {
    final Position.TPosition a1$8075 = arg$1.mem$start.call();
    return a1$8075;
  }
  final public static <α> boolean has$end(final Lazy<α> arg$1) {
    return true;
  }
  final public static <α> boolean has$start(final Lazy<α> arg$1) {
    return true;
  }
  final public static TRange chg$start(final TRange arg$1, final Lazy<Func.U<Position.TPosition, Position.TPosition>> arg$2) {
    return TRange.mk(
              Thunk.<Position.TPosition>nested((Lazy<Lazy<Position.TPosition>>)(() -> arg$2.call().apply(arg$1.mem$start))),
              arg$1.mem$end
            );
  }
  final public static TRange chg$end(final TRange arg$1, final Lazy<Func.U<Position.TPosition, Position.TPosition>> arg$2) {
    return TRange.mk(
              arg$1.mem$start,
              Thunk.<Position.TPosition>nested((Lazy<Lazy<Position.TPosition>>)(() -> arg$2.call().apply(arg$1.mem$end)))
            );
  }
  final public static Position.TPosition end(final TRange arg$1) {
    final Position.TPosition a2$8064 = arg$1.mem$end.call();
    return a2$8064;
  }
}
final public static TRange tokenToRange(final Tokens.TToken arg$1) {
  return TRange.mk(
            Position.TPosition.mk(Thunk.<Integer>lazy(arg$1.mem$line), Thunk.<Integer>lazy(arg$1.mem$col)),
            Position.TPosition.mk(
                  Thunk.<Integer>lazy(arg$1.mem$line),
                  Thunk.<Integer>shared((Lazy<Integer>)(() -> arg$1.mem$col + arg$1.mem$value.length()))
                )
          );
}

}
