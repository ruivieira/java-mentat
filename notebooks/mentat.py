import sys
import jpype
import jpype.imports


path=[
    "../target/mentat-0.0.1-jar-with-dependencies.jar",
]

# Launch the JVM
try:
    jpype.startJVM(classpath=path)

    from java.lang import Thread

    if not Thread.isAttached:
        jpype.attachThreadToJVM()
except OSError:
    print("JVM already started")

from network.models import ErdosRenyi as _ErdosRenyi
from explainability.fairness import CounterfactualFairness as _CounterfactualFairness

CounterfactualFairness = _CounterfactualFairness
ErdosRenyi = _ErdosRenyi