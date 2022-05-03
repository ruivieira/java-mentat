import sys
import jpype
import jpype.imports
import pandas as pd
import numpy as np

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
from metrics import ROC as _ROC

CounterfactualFairness = _CounterfactualFairness
ErdosRenyi = _ErdosRenyi
ROC = _ROC