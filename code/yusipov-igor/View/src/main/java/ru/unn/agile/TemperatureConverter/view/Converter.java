package ru.unn.agile.TemperatureConverter.view;

import ru.unn.agile.TemperatureConverter.model.TemperatureScaleName;
import ru.unn.agile.TemperatureConverter.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.*;

public final class Converter {
    private JComboBox<TemperatureScaleName> comboBoxScales;
    private JTextField textFieldInput;
    private JTextField textFieldResult;
    private JButton buttonConvert;
    private JTextField textFieldStatus;
    private JPanel mainPanel;

    private ViewModel viewModel;

    private Converter() { }

    private Converter(final ViewModel viewModel) {
        this.viewModel = viewModel;
        bindTemperatureScalesToComboBox();
        backBind();

        buttonConvert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                viewModel.convert();
                backBind();
            }
        });

        comboBoxScales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                backBind();
            }
        });

        KeyAdapter keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent keyEvent) {
                bind();
                viewModel.parse();
                backBind();
            }
        };
        textFieldInput.addKeyListener(keyListener);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Converter");
        frame.setContentPane(new Converter(new ViewModel()).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bindTemperatureScalesToComboBox() {
        TemperatureScaleName[] scales = TemperatureScaleName.values();
        comboBoxScales.setModel(new JComboBox<>(scales).getModel());
    }

    private void bind() {
        viewModel.setInputTemperature(textFieldInput.getText());
        viewModel.setScale((TemperatureScaleName) comboBoxScales.getSelectedItem());
    }

    private void backBind() {
        comboBoxScales.setSelectedItem(viewModel.getScale());
        buttonConvert.setEnabled(viewModel.isConvertButtonEnabled());
        textFieldResult.setText(viewModel.getResultTemperature());
        textFieldStatus.setText(viewModel.getStatusName());
    }
}
