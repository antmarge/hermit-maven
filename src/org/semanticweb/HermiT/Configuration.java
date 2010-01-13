/* Copyright 2009 by the Oxford University Computing Laboratory
   
   This file is part of HermiT.

   HermiT is free software: you can redistribute it and/or modify
   it under the terms of the GNU Lesser General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   HermiT is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Lesser General Public License for more details.
   
   You should have received a copy of the GNU Lesser General Public License
   along with HermiT.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.semanticweb.HermiT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.HermiT.model.AtomicConcept;
import org.semanticweb.HermiT.monitor.TableauMonitor;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;
import org.semanticweb.owlapi.reasoner.UndeclaredEntityPolicy;

public class Configuration implements Serializable,Cloneable,OWLReasonerConfiguration {
    private static final long serialVersionUID=7741510316249774519L;

    public static enum TableauMonitorType {
        NONE,TIMING,TIMING_WITH_PAUSE,DEBUGGER_NO_HISTORY,DEBUGGER_HISTORY_ON
    }

    public static enum DirectBlockingType {
        SINGLE,PAIR_WISE,OPTIMAL
    }

    public static enum BlockingStrategyType {
        ANYWHERE,ANCESTOR,COMPLEX_CORE,SIMPLE_CORE,OPTIMAL
    }

    public static enum BlockingSignatureCacheType {
        CACHED,NOT_CACHED
    }

    public static enum ExistentialStrategyType {
        CREATION_ORDER,EL,INDIVIDUAL_REUSE
    }
    
    public WarningMonitor warningMonitor;
    public ReasonerProgressMonitor reasonerProgressMonitor;
    public Configuration.TableauMonitorType tableauMonitorType;
    public Configuration.DirectBlockingType directBlockingType;
    public Configuration.BlockingStrategyType blockingStrategyType;
    public Configuration.BlockingSignatureCacheType blockingSignatureCacheType;
    public Configuration.ExistentialStrategyType existentialStrategyType;
    public boolean checkClauses;
    public boolean ignoreUnsupportedDatatypes;
    public TableauMonitor monitor;
    public HashMap<String,Object> parameters;
    public long individualTaskTimeout;
    public boolean bufferChanges;
    public IndividualNodeSetPolicy individualNodeSetPolicy;
    public UndeclaredEntityPolicy undeclaredEntityPolicy;
    
    public Configuration() {
        warningMonitor=null;
        reasonerProgressMonitor=null;
        tableauMonitorType=Configuration.TableauMonitorType.NONE;
        directBlockingType=Configuration.DirectBlockingType.OPTIMAL;
        blockingStrategyType=Configuration.BlockingStrategyType.ANYWHERE;
        blockingSignatureCacheType=Configuration.BlockingSignatureCacheType.CACHED;
        existentialStrategyType=Configuration.ExistentialStrategyType.CREATION_ORDER;
        checkClauses=true;
        ignoreUnsupportedDatatypes=false;
        monitor=null;
        parameters=new HashMap<String,Object>();
        individualTaskTimeout=-1;
        bufferChanges=true;
        individualNodeSetPolicy=IndividualNodeSetPolicy.BY_NAME;
        undeclaredEntityPolicy=UndeclaredEntityPolicy.ALLOW;
    }
    protected void setIndividualReuseStrategyReuseAlways(Set<? extends AtomicConcept> concepts) {
        parameters.put("IndividualReuseStrategy.reuseAlways",concepts);
    }
    public void loadIndividualReuseStrategyReuseAlways(File file) throws IOException {
        Set<AtomicConcept> concepts=loadConceptsFromFile(file);
        setIndividualReuseStrategyReuseAlways(concepts);
    }
    protected void setIndividualReuseStrategyReuseNever(Set<? extends AtomicConcept> concepts) {
        parameters.put("IndividualReuseStrategy.reuseNever",concepts);
    }
    public void loadIndividualReuseStrategyReuseNever(File file) throws IOException {
        Set<AtomicConcept> concepts=loadConceptsFromFile(file);
        setIndividualReuseStrategyReuseNever(concepts);
    }
    protected Set<AtomicConcept> loadConceptsFromFile(File file) throws IOException {
        Set<AtomicConcept> result=new HashSet<AtomicConcept>();
        BufferedReader reader=new BufferedReader(new FileReader(file));
        try {
            String line=reader.readLine();
            while (line!=null) {
                result.add(AtomicConcept.create(line));
                line=reader.readLine();
            }
            return result;
        }
        finally {
            reader.close();
        }
    }
    @SuppressWarnings("unchecked")
    public Configuration clone() {
        try {
            Configuration result=(Configuration)super.clone();
            result.parameters=(HashMap<String,Object>)parameters.clone();
            return result;
        }
        catch (CloneNotSupportedException cantHappen) {
            return null;
        }
    }
    public static interface WarningMonitor {
        void warning(String warning);
    }
    public long getTimeOut() {
        return individualTaskTimeout;
    }
	public IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
		return individualNodeSetPolicy;
	}
	public ReasonerProgressMonitor getProgressMonitor() {
		return reasonerProgressMonitor;
	}
	public UndeclaredEntityPolicy getUndeclaredEntityPolicy() {
		return undeclaredEntityPolicy;
	}
}