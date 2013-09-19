/**
 * Copyright 2012 Alessandro Bahgat Shehata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abahgat.suffixtree;

import static com.abahgat.suffixtree.Utils.getSubstrings;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

public class SuffixTreeTest extends TestCase {

    public void testBasicTreeGeneration() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();

        String word = "cacao";
        in.put(word, 0);

        /* test that every substring is contained within the tree */
        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
        }
        assertNull(in.search("caco"));
        assertNull(in.search("cacaoo"));
        assertNull(in.search("ccacao"));

        in = new GeneralizedSuffixTree();
        word = "bookkeeper";
        in.put(word, 0);
        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
        }
        assertNull(in.search("books"));
        assertNull(in.search("boke"));
        assertNull(in.search("ookepr"));
    }

    public void testWeirdword() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();

        String word = "cacacato";
        in.put(word, 0);

        /* test that every substring is contained within the tree */
        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
        }
    }

    public void testDouble() {
        // test whether the tree can handle repetitions
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String word = "cacao";
        in.put(word, 0);
        in.put(word, 1);

        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
            assertTrue(in.search(s).contains(1));
        }
    }

    public void testBananaAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[] {"banana", "bano", "ba"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
            }

        }

        // verify post-addition
        for (int i = 0; i < words.length; ++i) {
            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i));
            }
        }

        // add again, to see if it's stable
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i + words.length);

            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i + words.length));
            }
        }

    }

    public void testAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[] {"cacaor" , "caricato", "cacato", "cacata", "caricata", "cacao", "banana"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
            }
        }
        // verify post-addition
        for (int i = 0; i < words.length; ++i) {
            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
            }
        }

        // add again, to see if it's stable
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i + words.length);

            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i + words.length));
            }
        }
        
        in.computeCount();
        testResultsCount(in.getRoot());

        assertNull(in.search("aoca"));
    }

    public void testSampleAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[] {"libertypike",
            "franklintn",
            "carothersjohnhenryhouse",
            "carothersezealhouse",
            "acrossthetauntonriverfromdightonindightonrockstatepark",
            "dightonma",
            "dightonrock",
            "6mineoflowgaponlowgapfork",
            "lowgapky",
            "lemasterjohnjandellenhouse",
            "lemasterhouse",
            "70wilburblvd",
            "poughkeepsieny",
            "freerhouse",
            "701laurelst",
            "conwaysc",
            "hollidayjwjrhouse",
            "mainandappletonsts",
            "menomoneefallswi",
            "mainstreethistoricdistrict",
            "addressrestricted",
            "brownsmillsnj",
            "hanoverfurnace",
            "hanoverbogironfurnace",
            "sofsavannahatfergusonaveandbethesdard",
            "savannahga",
            "bethesdahomeforboys",
            "bethesda"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
            }


        }
        // verify post-addition
        for (int i = 0; i < words.length; ++i) {
            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i));
            }
        }

        // add again, to see if it's stable
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i + words.length);

            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i + words.length));
            }
        }

        in.computeCount();
        testResultsCount(in.getRoot());

        assertNull(in.search("aoca"));
    }

    private void testResultsCount(Node n) {
        for (Edge e : n.getEdges().values()) {
            assertEquals(n.getData(-1).size(), n.getResultCount());
            testResultsCount(e.getDest());
        }
    }

    /* testing a test method :) */
    public void testGetSubstrings() {
        Collection<String> exp = new HashSet<String>();
        exp.addAll(Arrays.asList(new String[] {"w", "r", "d", "wr", "rd", "wrd"}));
        Collection<String> ret = getSubstrings("wrd");
        assertTrue(ret.equals(exp));
    }
    
    //Helper method for testSearchMatchingSuffix
    private Set<String> naiveSuffixGen(String[] arr, String regex) {
		Set<String> complete = new HashSet<String>();
		for(int i = 0; i < arr.length; i++) {
			String comp = "";
			String t = arr[i];
			for(int j = t.length() - 1; j >= 0; j--) {
				comp = t.charAt(j) + comp;
				complete.add(comp);
			}
		}
	
 		Iterator<String> it = complete.iterator();
		while(it.hasNext()) {
			String s = it.next();
			if(!s.matches(regex)) {
				it.remove();
			}
		}
		return complete;
	}
    
    public void testSearchMatchingSuffix() {
    	//Matches a string containing two $c 
    	String regex = "(^|.*?\\s)\\$c.*?\\s\\$c[^A-Za-z0-9_$].*?$";
    	
		String[] arr = {
			"$c or $c?", 
			"$c is much better than $c", 
			"15 $c is cooler than that!", 
			"If I had 5$ for everytime I heard that, I would have $5", 
			"Two $c for the price of $c",
			"Peanut butter jelly", 
			" ", 
			"$$$$$$ #end", 
			"Coconut banana power!", 
			"I am almost as good in $c as in $c."
		};
    	
		Set<String> complete = naiveSuffixGen(arr, regex);
		GeneralizedSuffixTree tree = new GeneralizedSuffixTree();
		
		for(int i = 0; i < arr.length; i++) {
			tree.put(arr[i], i);
		}
		
		Set<String> set = tree.searchMatchingSuffix(regex);
		List<String> treeList = new ArrayList<String>(set);
		List<String> completeList = new ArrayList<String>(complete);
		
		Collections.sort(treeList);
		Collections.sort(completeList);
		
		assertArrayEquals(treeList.toArray(new String[treeList.size()]), completeList.toArray(new String[completeList.size()]));
		
		//Matches a string containing two C
		String regex1 = "^.*?C.*?C.*?$";
						
		String[] test1 = {
			"ABBCAABBAABBACAAAB",
			"CC",
			"ABCABC",
			"CAC",
			"ABBBABB",
			"ABC",
			"AAAABBABABBACC",
			""	
		};
		
		Set<String> comp = naiveSuffixGen(test1, regex1);
		GeneralizedSuffixTree ki = new GeneralizedSuffixTree();
		
		for(int i = 0; i < test1.length; i++) {
			ki.put(test1[i], i);
		}
		
		Set<String> col = ki.searchMatchingSuffix(regex1);
		List<String> kiList = new ArrayList<String>(col);
		List<String> compList = new ArrayList<String>(comp);
		
		Collections.sort(kiList);
		Collections.sort(compList);
		
		
		assertArrayEquals(kiList.toArray(new String[kiList.size()]), compList.toArray(new String[compList.size()]));
				
    }
    
    public void testCreateDotGraph() {
    	String[] arr = {
    			"ABBCAABBAABBACAAAB",
    			"CC",
    			"ABCABC",
    			"CAC",
    			"ABBBABB",
    			"ABC",
    			"AAAABBABABBACC",
    			""	
    		};
    	
		GeneralizedSuffixTree tree = new GeneralizedSuffixTree();
		for(int i = 0; i < arr.length; i++) {
			tree.put(arr[i], i);
		}
		
		tree.createDotGraph();
		
		File f = new File("out.gv");
		assertTrue(f.exists());
		f.delete();
		assertFalse(f.exists());
	}

}
