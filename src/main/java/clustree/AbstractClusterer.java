package clustree;

import com.github.javacliparser.FlagOption;
import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Instances;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import moa.cluster.Clustering;
import moa.clusterers.Clusterer;
import moa.core.Measurement;
import moa.core.ObjectRepository;
import moa.core.StringUtils;
import moa.gui.AWTRenderer;
import moa.options.AbstractOptionHandler;
import moa.tasks.TaskMonitor;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class AbstractClusterer extends AbstractOptionHandler implements clustree.Clusterer {
    protected InstancesHeader modelContext;
    protected double trainingWeightSeenByModel = 0.0D;
    protected int randomSeed = 1;
    protected IntOption randomSeedOption;
    public FlagOption evaluateMicroClusteringOption;
    protected Random clustererRandom;
    protected Clustering clustering;

    public String getPurposeString() {
        return "MOA Clusterer: " + this.getClass().getCanonicalName();
    }

    public AbstractClusterer() {
        if (this.isRandomizable()) {
            this.randomSeedOption = new IntOption("randomSeed", 'r', "Seed for random behaviour of the Clusterer.", 1);
        }

        if (this.implementsMicroClusterer()) {
            this.evaluateMicroClusteringOption = new FlagOption("evaluateMicroClustering", 'M', "Evaluate the underlying microclustering instead of the macro clustering");
        }

    }


    public void prepareForUseImpl(TaskMonitor monitor, ObjectRepository repository) {
        if (this.randomSeedOption != null) {
            this.randomSeed = this.randomSeedOption.getValue();
        }

        if (!this.trainingHasStarted()) {
            this.resetLearning();
        }

        this.clustering = new Clustering();
    }

    public void setModelContext(InstancesHeader ih) {
        if (ih != null && ih.classIndex() < 0) {
            throw new IllegalArgumentException("Context for a Clusterer must include a class to learn");
        } else if (!this.trainingHasStarted() || this.modelContext == null || ih != null && contextIsCompatible(this.modelContext, ih)) {
            this.modelContext = ih;
        } else {
            throw new IllegalArgumentException("New context is not compatible with existing model");
        }
    }

    public InstancesHeader getModelContext() {
        return this.modelContext;
    }

    public void setRandomSeed(int s) {
        this.randomSeed = s;
        if (this.randomSeedOption != null) {
            this.randomSeedOption.setValue(s);
        }

    }

    public boolean trainingHasStarted() {
        return this.trainingWeightSeenByModel > 0.0D;
    }

    public double trainingWeightSeenByModel() {
        return this.trainingWeightSeenByModel;
    }

    public void resetLearning() {
        this.trainingWeightSeenByModel = 0.0D;
        if (this.isRandomizable()) {
            this.clustererRandom = new Random((long)this.randomSeed);
        }

        this.resetLearningImpl();
    }

    public void trainOnInstance(Instance inst) {
        if (inst.weight() > 0.0D) {
            this.trainingWeightSeenByModel += inst.weight();
            this.trainOnInstanceImpl(inst);
        }

    }

    public Measurement[] getModelMeasurements() {
        List<Measurement> measurementList = new LinkedList();
        measurementList.add(new Measurement("model training instances", this.trainingWeightSeenByModel()));
        measurementList.add(new Measurement("model serialized size (bytes)", (double)this.measureByteSize()));
        Measurement[] modelMeasurements = this.getModelMeasurementsImpl();
        if (modelMeasurements != null) {
            Measurement[] var3 = modelMeasurements;
            int var4 = modelMeasurements.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Measurement measurement = var3[var5];
                measurementList.add(measurement);
            }
        }

        moa.clusterers.Clusterer[] subModels = this.getSubClusterers();
        if (subModels != null && subModels.length > 0) {
            List<Measurement[]> subMeasurements = new LinkedList();
            moa.clusterers.Clusterer[] var12 = subModels;
            int var14 = subModels.length;

            int var7;
            for(var7 = 0; var7 < var14; ++var7) {
                moa.clusterers.Clusterer subModel = var12[var7];
                if (subModel != null) {
                    subMeasurements.add(subModel.getModelMeasurements());
                }
            }

            Measurement[] avgMeasurements = Measurement.averageMeasurements((Measurement[][])subMeasurements.toArray(new Measurement[subMeasurements.size()][]));
            Measurement[] var15 = avgMeasurements;
            var7 = avgMeasurements.length;

            for(int var16 = 0; var16 < var7; ++var16) {
                Measurement measurement = var15[var16];
                measurementList.add(measurement);
            }
        }

        return (Measurement[])measurementList.toArray(new Measurement[measurementList.size()]);
    }

    public void getDescription(StringBuilder out, int indent) {
        StringUtils.appendIndented(out, indent, "Model type: ");
        out.append(this.getClass().getName());
        StringUtils.appendNewline(out);
        Measurement.getMeasurementsDescription(this.getModelMeasurements(), out, indent);
        StringUtils.appendNewlineIndented(out, indent, "Model description:");
        StringUtils.appendNewline(out);
        if (this.trainingHasStarted()) {
            this.getModelDescription(out, indent);
        } else {
            StringUtils.appendIndented(out, indent, "Model has not been trained.");
        }

    }

    public moa.clusterers.Clusterer[] getSubClusterers() {
        return null;
    }

    public moa.clusterers.Clusterer copy() {
        return (Clusterer)super.copy();
    }

    public String getClassNameString() {
        return InstancesHeader.getClassNameString(this.modelContext);
    }

    public String getClassLabelString(int classLabelIndex) {
        return InstancesHeader.getClassLabelString(this.modelContext, classLabelIndex);
    }

    public String getAttributeNameString(int attIndex) {
        return InstancesHeader.getAttributeNameString(this.modelContext, attIndex);
    }

    public String getNominalValueString(int attIndex, int valIndex) {
        return InstancesHeader.getNominalValueString(this.modelContext, attIndex, valIndex);
    }

    public static boolean contextIsCompatible(InstancesHeader originalContext, InstancesHeader newContext) {
        if (newContext.numClasses() < originalContext.numClasses()) {
            return false;
        } else if (newContext.numAttributes() < originalContext.numAttributes()) {
            return false;
        } else {
            int oPos = 0;

            for(int nPos = 0; oPos < originalContext.numAttributes(); ++nPos) {
                if (oPos == originalContext.classIndex()) {
                    ++oPos;
                    if (oPos >= originalContext.numAttributes()) {
                        break;
                    }
                }

                if (nPos == newContext.classIndex()) {
                    ++nPos;
                }

                if (originalContext.attribute(oPos).isNominal()) {
                    if (!newContext.attribute(nPos).isNominal()) {
                        return false;
                    }

                    if (newContext.attribute(nPos).numValues() < originalContext.attribute(oPos).numValues()) {
                        return false;
                    }
                } else {
                    assert originalContext.attribute(oPos).isNumeric();

                    if (!newContext.attribute(nPos).isNumeric()) {
                        return false;
                    }
                }

                ++oPos;
            }

            return true;
        }
    }

    public AWTRenderer getAWTRenderer() {
        return null;
    }

    public abstract void resetLearningImpl();

    public abstract void trainOnInstanceImpl(Instance var1);

    protected abstract Measurement[] getModelMeasurementsImpl();

    public abstract void getModelDescription(StringBuilder var1, int var2);

    protected static int modelAttIndexToInstanceAttIndex(int index, Instance inst) {
        return inst.classIndex() > index ? index : index + 1;
    }

    protected static int modelAttIndexToInstanceAttIndex(int index, Instances insts) {
        return insts.classIndex() > index ? index : index + 1;
    }

    public boolean implementsMicroClusterer() {
        return false;
    }

    public boolean keepClassLabel() {
        return false;
    }

    public Clustering getMicroClusteringResult() {
        return null;
    }
}
