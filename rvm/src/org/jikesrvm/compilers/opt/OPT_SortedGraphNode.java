/*
 * This file is part of Jikes RVM (http://jikesrvm.sourceforge.net).
 * The Jikes RVM project is distributed under the Common Public License (CPL).
 * A copy of the license is included in the distribution, and is also
 * available at http://www.opensource.org/licenses/cpl1.0.php
 *
 * (C) Copyright IBM Corp. 2001
 */
package org.jikesrvm.compilers.opt;

import java.util.Enumeration;

// For now, OPT_SortedGraphNode inherits from DoublyLinkedList only
// for compatibility
// with the current implementation of OPT_BasicBlock. 
// In the future, this inheritance will be removed.
// Therefore, the user of this class should ignore that
// this class extends DoublyLinkedList.

// In short, it does NOT INHERIT FROM DoublyLinkedListElement.
public abstract class OPT_SortedGraphNode extends OPT_SpaceEffGraphNode {

  // Return enumerator for all the in nodes.
  public abstract Enumeration<? extends OPT_SortedGraphNode> getInNodes();  // should be overridden 
  // by a subclass

  // Return enumerator for all the out nodes.

  public abstract Enumeration<? extends OPT_SortedGraphNode> getOutNodes(); // should be overridden by a
  // subclass

  public OPT_SortedGraphNode getSortedNext(boolean forward) {
    if (forward) {
      return sortedNext;
    } else {
      return sortedPrev;
    }
  }

  public OPT_SortedGraphNode getForwardSortedNext() {
    return sortedNext;
  }

  public OPT_SortedGraphNode getBackwardSortedNext() {
    return sortedPrev;
  }

  public void setSortedNext(OPT_SortedGraphNode next, boolean forward) {
    if (forward) {
      sortedNext = next;
    } else {
      sortedPrev = next;
    }
  }

  // preferred interface
  public void setForwardSortNumber(int number) {
    forwardSortNumber = number;
  }

  public int getForwardSortNumber() {
    return forwardSortNumber;
  }

  public void setBackwardSortNumber(int number) {
    backwardSortNumber = number;
  }

  public int getBackwardSortNumber() {
    return backwardSortNumber;
  }

  // probably less efficient than above, but more flexible
  public void setSortNumber(int number, boolean forward) {
    if (forward) {
      forwardSortNumber = number;
    } else {
      backwardSortNumber = number;
    }
  }

  public int getSortNumber(boolean forward) {
    if (forward) {
      return forwardSortNumber;
    } else {
      return backwardSortNumber;
    }
  }

  public void setSortNumber(int number) {
    forwardSortNumber = number;
  }

  // Do we need this?
  //  public int isForwardSorted(OPT_SortedGraphNode node) { 
  //    return forwardSortNumber - node.forwardSortNumber;
  //  }
  public static int getNewSortMarker(OPT_SortedGraphNode anchor) {
    if (currentSortMarker == Integer.MAX_VALUE) {
      OPT_SortedGraphNode current;
      for (current = anchor; current != null; current = current.sortedPrev) {
        current.sortMarker = Integer.MIN_VALUE;
      }
      for (current = anchor; current != null; current = current.sortedNext) {
        current.sortMarker = Integer.MIN_VALUE;
      }
      currentSortMarker = Integer.MIN_VALUE;
    }
    return ++currentSortMarker;
  }

  int sortMarker = Integer.MIN_VALUE;
  private static int currentSortMarker = Integer.MIN_VALUE;

  public int getSortMarker() {
    return sortMarker;
  }

  public void setSortMarker(int sortMarker) {
    this.sortMarker = sortMarker;
  }

  public boolean isSortMarkedWith(int sortMarker) {
    return (this.sortMarker >= sortMarker);
  }

  protected OPT_SortedGraphNode sortedPrev = null, sortedNext = null;
  protected int forwardSortNumber;
  protected int backwardSortNumber;
}
