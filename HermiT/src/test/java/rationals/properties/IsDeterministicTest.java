/*______________________________________________________________________________
 * 
 * Copyright 2005 Arnaud Bailly - NORSYS/LIFL
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * (1) Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 * (2) Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *
 * (3) The name of the author may not be used to endorse or promote
 *     products derived from this software without specific prior
 *     written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on 19 avr. 2005
 *
 */
package rationals.properties;

import junit.framework.TestCase;
import rationals.Automaton;
import rationals.NoSuchStateException;
import rationals.State;
import rationals.Transition;
import rationals.converters.ConverterException;
import rationals.converters.Expression;
import rationals.transformations.ToDFA;

/**
 * @author nono
 * @version $Id: IsDeterministicTest.java 2 2006-08-24 14:41:48Z oqube $
 */
public class IsDeterministicTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for IsDeterministicTest.
     * @param arg0
     */
    public IsDeterministicTest(String arg0) {
        super(arg0);
    }

    public void testTrue() throws ConverterException {
        Automaton a = new ToDFA().transform(new Expression().fromString("a(b+a*c)bc*"));
        assertTrue(new IsDeterministic().test(a));
    }
    
    public void testFalse1() throws NoSuchStateException {
        Automaton a = new Automaton();
        State s1 = a.addState(true,false);
        State s2 = a.addState(true,false);
        State s3 = a.addState(false,true);
        a.addTransition(new Transition(s1,"a",s2));
        a.addTransition(new Transition(s2,"b",s1));
        a.addTransition(new Transition(s1,"b",s3));
        a.addTransition(new Transition(s3,"a",s2));
        assertTrue(!new IsDeterministic().test(a));
        
    }

    public void testFalse2() throws NoSuchStateException {
        Automaton a = new Automaton();
        State s1 = a.addState(true,false);
        State s2 = a.addState(false,false);
        State s3 = a.addState(false,true);
        a.addTransition(new Transition(s1,"a",s2));
        a.addTransition(new Transition(s2,"b",s1));
        a.addTransition(new Transition(s2,"b",s3));
        a.addTransition(new Transition(s3,"a",s2));
        assertTrue(!new IsDeterministic().test(a));
    }
}
