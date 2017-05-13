package java.util.stream;

import java.util.function.Supplier;

abstract interface TerminalSink<T, R>
  extends Sink<T>, Supplier<R>
{}


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/TerminalSink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */