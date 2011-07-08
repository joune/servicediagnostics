OSGi Service Diagnostics and WebConsole Plugin
==============================================

This projects aims at easing diagnostics of OSGi services and finding about missing dependencies.

Typically in a large system with many cascading dependencies managed by different trackers such as DeclarativeService, DependencyManager or others, tracking the root cause of a top level service not being started can become very cumbersome. When building service oriented architectures, it is often the case that a single missing requirement will lock a full stack of services, but to find that one requirement is like finding a needle in a haystack!

The basic idea here is to ask each dependency manager instance about its unresolved dependencies, merge all answers and filter the result to keep only the root causes. 

Typically, if A depends on B which depends on C which depends on D, and D is nowhere to be found, I need only show the "C -> D" missing requirement; if D is resolved, then the whole stack is unlocked.

Similarly, if D is known by another dependency management system, but unregistered because it depends on E which is missing, then only the "D -> E" requirement is relevant. 

Project organization:
=====================
* servicediagnostics: the API package. It holds the main service interface as well as the plugin interface, to extend to other dependency management systems

* servicediagnostics.impl: the implementation package. It contains plugins implementations for org.apache.felix.scr and org.apache.felix.dependencymanager, as well as the main service implementation.

* servicediagnostics.webconsole: a Felix WebConsole plugin that displays a graphical view of the diagnostics result. Alternatively it can also show a graph of all services currently registered in the service registry and the bundles using them. See Screenshot-notavail.png and Screenshot-all.png. 

How to build the project:
=========================
I haven't quite figured out how to use the bnd4sbt sbt plugin, and i'm not a major fan of maven, so I'll start with a basic ant build. This may change in the future.

> ant

How to run:
===========
I'm providing a simple run script just to try it out:
> ./run.sh

Issues & TODOs:
===============
* use of JSONObject in Servlet.scala is a bit awkward, but Scala's native json is missing the quotes... i must be missing something!
* no support for service properties; needs to be added.
* no support for iPojo, Blueprint, basic ServiceTrackers... more plugins could be developed. I only wrote the ones i'm using.
* proper tests!
* build with sbt?