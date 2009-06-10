/***********************************************************
 * Copyright 2009
 * Kirby Files, ksfiles@gmail.com
 * Suresh Tripath, workingsuresh@gmail.com
 * All Rights Reserved
 */

package com.prodco.netview.server;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author kfiles
 * Implements a parameterized container that holds two typed objects. C++ STL
 * programmers will find this container very similar to std::pair. Useful for
 * storing value tuples in Hashtables and other containers without creating
 * overly complex recursive hashtable lookups. Why doesn't java.util implement
 * this already?
*/
public final class Pair<K, V> implements Serializable
    {
    private K first;
    private V second;

    public Pair(K first, V second)
        {
        this.first = first;
        this.second = second;
        }

    /**
     * @return the first member of the pair
     */
    public K first()
        {
        return first;
        }

    /**
     * @return the second member of the pair
     */
    public V second()
        {
        return second;
        }

    /**
     * Compares the specified Object with this Pair for equality.
     * 
     * @param o
     *            object to be compared for equality with this Pair.
     * @return true if the specified Object is equal to this Pair.
     */
    public boolean equals(Object o)
        {
        if (o == this)
            return true;

        if (!(o instanceof Pair))
            return false;

        try
            {
            Pair<K, V> t = (Pair<K, V>) o;
            if (null == t.first()) 
                {
                if (null != this.first())
                    return false;
                if (null == t.second())
                    return (null == this.second());
                else
                    return (t.second().equals( this.second() ));
                }
            else
                {
                if (null == t.second())
                    return (t.first().equals( this.first() ));
                else
                    return (( t.first().equals( this.first() ) ) &&
                        ( t.second().equals( this.second() )));
                }
            }
        catch (ClassCastException unused)
            {
            return false;
            }
        }

    /**
     * Returns the hash code value for this Pair.
     */
    public int hashCode()
        {
        if (null == first)
            {
            if (null == second)
                return 0;
            return second.hashCode();
            }
        if (null == second)
            return first.hashCode();
        return (first.hashCode() ^ second.hashCode());
        }

    /**
     * Save the state of the Pair to a stream (i.e., serialize it). Just uses
     * the defaultWriteObject() for now.
     * 
     * @serialData The first and second value of the pair are emitted in that
     *             order.
     */
    private void writeObject(java.io.ObjectOutputStream s) throws IOException
        {
        // Write out the length, threshold, loadfactor
        s.defaultWriteObject();
        }

    /**
     * Reconstitute the Pair from a stream (i.e., deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s) throws IOException,
        ClassNotFoundException
        {
        // Read in the first and second member values.
        s.defaultReadObject();
        }

    }