package Metrics;

import java.util.LinkedList;
import Model.Foraminifera;

public class MetricsEngine {

    private Foraminifera foraminifera;
    private LinkedList<IMetricCalculator> calculators;

    public MetricsEngine(Foraminifera foraminifera) {
        this.foraminifera = foraminifera;

        calculators = new LinkedList<>();

        calculators.add(new XCalculator());
        calculators.add(new YCalculator());
        calculators.add(new ZCalculator());
        calculators.add(new VolumeCalculator());
        calculators.add(new MaterialCalculator());
    }

    public LinkedList<CalculationResult> CalculateMetrics(){
        LinkedList<CalculationResult> result = new LinkedList<>();

        for (IMetricCalculator calc : calculators) {
            double calcResult = calc.CalculateMetric(foraminifera);
            String name = calc.GetName();

            result.add(new CalculationResult(name, calcResult));
        }

        return result;
    }

    public void AddCalculator(IMetricCalculator calculator){
        calculators.add(calculator);
    }
}

