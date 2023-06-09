import tester.*;
import java.util.function.Predicate;

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  void updatePrevious(ANode<T> prev) {
    this.prev = prev;
  }

  void updateNext(ANode<T> next) {
    this.next = next;
  }

  int size() {
    return 0;
  }

  int sizeStarter() {
    return this.next.size();
  }

  void addToHead(T data) {
    new Node<T>(data, this.next, this);
  }

  void addToTail(T data) {
    new Node<T>(data, this, this.prev);
  }

  T removeFromHead() {
    return this.next.removeHead();
  }

  T removeFromTail() {
    return this.prev.removeTail();
  }

  T removeHead() {
    throw new RuntimeException("List is Empty");
  }
  
  T removeTail() {
    throw new RuntimeException("List is Empty");
  }

  ANode<T> find(Predicate<T> where) {
    return this;
  }

  ANode<T> findStarter(Predicate<T> where) {
    return this.next.find(where);
  }
}

class Node<T> extends ANode<T> {
  T data;

  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  Node(T data, ANode<T> next, ANode<T> prev) {
    if (prev == null || next == null) {
      throw new IllegalArgumentException("Cannot assign null valuesto previous or next nodes");
    }
    this.data = data;
    this.next = next;
    this.next.updatePrevious(this);
    this.prev = prev;
    this.prev.updateNext(this);
  }

  int size() {
    return 1 + this.next.size();
  }

  T removeHead() {
    this.prev.updateNext(this.next);
    this.next.updatePrevious(this.prev);
    return this.data;
  }
  
  T removeTail() {
    this.next.updatePrevious(this.prev);
    this.prev.updateNext(this.next);
    return this.data;
  }

  ANode<T> find(Predicate<T> where) {
    if (where.test(this.data)) {
      return this;
    }
    return this.next.find(where);
  }
}

class Sentinel<T> extends ANode<T> {

  Sentinel() {
    this.next = this;
    this.prev = this;
  }
}

class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  int size() {
    return header.sizeStarter();
  }

  void addAtHead(T data) {
    this.header.addToHead(data);
  }

  void addAtTail(T data) {
    this.header.addToTail(data);
  }

  T removeFromHead() {
    return this.header.removeFromHead();
  }

  T removeFromTail() {
    return this.header.removeFromTail();
  }

  ANode<T> find(Predicate<T> where) {
    return this.header.findStarter(where);
  }
}

class ExamplesDeque {
  Deque<String> deque1 = new Deque<String>();
  Deque<String> deque2 = new Deque<String>();
  Deque<Integer> deque3 = new Deque<Integer>();

  ExamplesDeque() {
    this.reset();
  }

  void reset() {
    this.deque1 = new Deque<String>();
    this.deque2 = new Deque<String>();
    this.deque3 = new Deque<Integer>();
    new Node<String>("abc",
        new Node<String>("bcd", new Node<String>("cde",
            new Node<String>("def", this.deque2.header, this.deque2.header), this.deque2.header),
            this.deque2.header),
        this.deque2.header);
    new Node<Integer>(420,
        new Node<Integer>(69, new Node<Integer>(7,
            new Node<Integer>(560, this.deque3.header, this.deque3.header), this.deque3.header),
            this.deque3.header),
        this.deque3.header);
  }

  void testInitialization(Tester t) {
    this.reset();
    t.checkExpect(deque2, new Deque<String>());
  }

}