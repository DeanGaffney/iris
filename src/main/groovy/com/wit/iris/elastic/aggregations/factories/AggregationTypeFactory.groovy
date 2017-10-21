package com.wit.iris.elastic.aggregations.factories

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.buckets.DateHistogram
import com.wit.iris.elastic.aggregations.types.buckets.DateRange
import com.wit.iris.elastic.aggregations.types.buckets.Filter
import com.wit.iris.elastic.aggregations.types.buckets.GlobalAgg
import com.wit.iris.elastic.aggregations.types.buckets.Histogram
import com.wit.iris.elastic.aggregations.types.buckets.IpRange
import com.wit.iris.elastic.aggregations.types.buckets.Missing
import com.wit.iris.elastic.aggregations.types.buckets.Ranges
import com.wit.iris.elastic.aggregations.types.buckets.SignificantTerms
import com.wit.iris.elastic.aggregations.types.buckets.Terms
import com.wit.iris.elastic.aggregations.types.enums.BucketType
import com.wit.iris.elastic.aggregations.types.enums.MetricType
import com.wit.iris.elastic.aggregations.types.metric.Average
import com.wit.iris.elastic.aggregations.types.metric.Cardinality
import com.wit.iris.elastic.aggregations.types.metric.ExtendedStats
import com.wit.iris.elastic.aggregations.types.metric.Max
import com.wit.iris.elastic.aggregations.types.metric.Min
import com.wit.iris.elastic.aggregations.types.metric.Stats
import com.wit.iris.elastic.aggregations.types.metric.Sum
import com.wit.iris.elastic.aggregations.types.metric.ValueCount

/**
 * Created by dgaffney on 2/22/2017.
 */
class AggregationTypeFactory {

    static AggregationType getAggregationType(String aggregationType, Map aggMap){
        AggregationType aggType
        if(aggregationType.equalsIgnoreCase(MetricType.AVERAGE.getValue())){
            aggType = getAverage(aggMap)
        }else if(aggregationType.equalsIgnoreCase(MetricType.CARDINALITY.getValue())){
            aggType = getCardinality(aggMap)
        }else if(aggregationType.equalsIgnoreCase(MetricType.EXTENDED_STATS.getValue())){
            aggType = getExtendedStats(aggMap)
        }else if(aggregationType.equalsIgnoreCase(MetricType.MAX.getValue())){
            aggType = getMax(aggMap)
        }else if(aggregationType.equalsIgnoreCase(MetricType.MIN.getValue())){
            aggType = getMin(aggMap)
        }else if(aggregationType.equalsIgnoreCase(MetricType.SUM.getValue())){
            aggType = getSum(aggMap)
        }else if(aggregationType.equalsIgnoreCase(MetricType.STATS.getValue())){
            aggType = getStats(aggMap)
        }else if(aggregationType.equalsIgnoreCase(MetricType.VALUE_COUNT.getValue())){
            aggType = getValueCount(aggMap)
        }else if(aggregationType.equalsIgnoreCase(BucketType.TERMS.getValue())){
            aggType = getTerms(aggMap)
        }else if(aggregationType.equalsIgnoreCase(BucketType.DATE_HISTOGRAM.getValue())){
            aggType = getDateHistogram(aggMap)
        }else if(aggregationType.equalsIgnoreCase(BucketType.DATE_RANGE.getValue())){
            aggType = getDateRange(aggMap)
        }else if(aggregationType.equalsIgnoreCase(BucketType.MISSING.getValue())){
            aggType = getMissing(aggMap)
        }else if(aggregationType.equalsIgnoreCase(BucketType.RANGE.getValue())){
            aggType = getRanges(aggMap)
        }else if(aggregationType.equalsIgnoreCase(BucketType.SIGNIFICANT_TERMS.getValue())){
            aggType = getSignificantTerms(aggMap)
        }else if(aggregationType.equalsIgnoreCase(BucketType.HISTOGRAM.getValue())){
            aggType = getHistogram(aggMap)
        }else if(aggregationType.equalsIgnoreCase(BucketType.IP_RANGE.getValue())){
            aggType = getIpRange(aggMap)
        }else if(aggregationType.equalsIgnoreCase(BucketType.GLOBAL.getValue())){
            aggType = getGlobalAgg(aggMap)
        }else if(aggregationType.equalsIgnoreCase(BucketType.FILTER.getValue())){
            aggType = getFilter(aggMap)
        }
        return aggType
    }

    static AggregationType getAggregationType(String operation,String field, String dataType, int limit){
        AggregationType aggType
        if(operation.equalsIgnoreCase("terms")){
            aggType = new Terms(type: "terms", field: field, dataType: dataType,size :limit)
        }
        return aggType
    }
    
    /*--------------------------------------------------
                        METRICS
     ---------------------------------------------------*/
    static AggregationType getAverage(Map aggMap){
        return new Average(aggMap)
    }

    static AggregationType getCardinality(Map aggMap){
        return new Cardinality(aggMap)
    }

    static AggregationType getExtendedStats(Map aggMap){
        return new ExtendedStats(aggMap)
    }

    static AggregationType getMax(Map aggMap){
        return new Max(aggMap)
    }

    static AggregationType getMin(Map aggMap){
        return new Min(aggMap)
    }

    static AggregationType getStats(Map aggMap){
        return new Stats(aggMap)
    }

    static AggregationType getSum(Map aggMap){
        return new Sum(aggMap)
    }

    static AggregationType getValueCount(Map aggMap){
        return new ValueCount(aggMap)
    }

    /*--------------------------------------------------
                        BUCKETS
    ---------------------------------------------------*/
    
    static AggregationType getTerms(Map aggMap){
        return new Terms(aggMap)
    }

    static AggregationType getSignificantTerms(Map aggMap){
        return new SignificantTerms(aggMap)
    }

    static AggregationType getRanges(Map aggMap){
        return new Ranges(aggMap)
    }

    static AggregationType getMissing(Map aggMap){
        return new Missing(aggMap)
    }

    static AggregationType getIpRange(Map aggMap){
        return new IpRange(aggMap)
    }

    static AggregationType getHistogram(Map aggMap){
        return new Histogram(aggMap)
    }

    static AggregationType getGlobalAgg(Map aggMap){
        return new GlobalAgg(aggMap)
    }

    static AggregationType getFilter(Map aggMap){
        return new Filter(aggMap)
    }

    static AggregationType getDateRange(Map aggMap){
        return new DateRange(aggMap)
    }

    static AggregationType getDateHistogram(Map aggMap){
        return new DateHistogram(aggMap)
    }

    static AggregationType getFilterObject(String field, String value, String dataType){
        return new Filter (type: "filter", field: field, value: value, dataType:dataType)
    }

}
