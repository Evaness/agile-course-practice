package ru.unn.agile.TemperatureConverter.viewmodel;

import ru.unn.agile.TemperatureConverter.model.TemperatureConverter;
import ru.unn.agile.TemperatureConverter.model.TemperatureScaleName;

public class ViewModel {
    private String inputTemperature;
    private String resultTemperature;
    private Status status;
    private TemperatureScaleName scale;
    private boolean isConvertButtonEnable;

    public ViewModel() {
        inputTemperature = "";
        resultTemperature = "";
        status = Status.WAITING;
        scale = TemperatureScaleName.FAHRENHEIT;
        isConvertButtonEnable = false;
    }

    public String getInputTemperature() {
        return inputTemperature;
    }

    public String getResultTemperature() {
        return resultTemperature;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusName() {
        return status.toString();
    }

    public TemperatureScaleName getScale() {
        return scale;
    }

    public boolean isConvertButtonEnabled() {
        return isConvertButtonEnable;
    }

    public void setInputTemperature(final String inputTemperature) {
        this.inputTemperature = inputTemperature;
    }

    public void setScale(final TemperatureScaleName scale) {
        this.scale = scale;
    }

    public boolean parse() {
        if (inputTemperature.isEmpty()) {
            status = Status.WAITING;
            isConvertButtonEnable = false;
            return isConvertButtonEnable;
        }
        try {
            Double.parseDouble(inputTemperature);
            status = Status.READY;
            isConvertButtonEnable = true;
            return isConvertButtonEnable;
        } catch (Exception exception) {
            status = Status.BAD_FORMAT;
            isConvertButtonEnable = false;
            return isConvertButtonEnable;
        }
    }

    public void convert() {
        if (parse()) {
            TemperatureConverter converter = new TemperatureConverter(scale);
            double temperature = converter.convert(Double.parseDouble(inputTemperature));
            resultTemperature = Double.toString(temperature);
            status = Status.SUCCESS;
        }
    }
}
