import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.*;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
    private JTable table1;
    JFrame frame = new JFrame();
    Object[][] array = new String[0][0];
    // Заголовки столбцов
    private Object[] columnsHeader = new String[] { "№", "Шифр маршрута", "Страна", "Стоимость" };

    private DefaultTableCellRenderer cellRenderer;

    TouristJournal turJornal = new TouristJournal("Journal");
    
    String get_panel_info() {
        return ("Журнал: " 
            + turJornal.getName() 
            + "  |  Всего стран: " 
            + Integer.toString(turJornal.quantityItem()) + "/"
            + Integer.toString(turJornal.size()));
    }
    public GUI() {
        super("Журнал");
        
        array = turJornal.putTouristJournal();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание стандартной модели
        tableModel = new DefaultTableModel();
        // Определение столбцов
        tableModel.setColumnIdentifiers(columnsHeader);
        // Наполнение модели данными
        for (int i = 0; i < array.length; i++)
            tableModel.addRow(array[i]);

        JLabel statusLabel = new JLabel(get_panel_info());
        //statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        this.setLocation(725, 300);
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Файл"); // Кнопка в бар меню
        menuBar.add(file);
        this.setJMenuBar(menuBar);
        this.revalidate();

        JMenu table = new JMenu("Таблица"); // Кнопка в бар меню
        menuBar.add(table);
        this.setJMenuBar(menuBar);
        this.revalidate();

        JMenu sort = new JMenu("Сортировка");
        JMenu function = new JMenu("Дополнительные функции");

        JMenuItem standartSort = sort.add(new JMenuItem("Стандарт"));

        standartSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tableModel.setRowCount(0);
                array = turJornal.putTouristJournal();
                for (int i = 0; i < array.length; i++)
                    tableModel.addRow(array[i]);
                table1.setModel(tableModel);

                Box contents = new Box(BoxLayout.Y_AXIS);
                getContentPane().add(contents);
                statusLabel.setText(get_panel_info());
            }
        });

        JMenuItem scoresort = sort.add(new JMenuItem("Убывание стоимости"));

        scoresort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tableModel.setRowCount(0);
                array = turJornal.sortScoreDesc().putTouristJournal();
                for (int i = 0; i < array.length; i++)
                    tableModel.addRow(array[i]);

                table1.setModel(tableModel);
                //table1.setAutoCreateRowSorter(true);
                Box contents = new Box(BoxLayout.Y_AXIS);
                getContentPane().add(contents);
                statusLabel.setText(get_panel_info());
            }
        });

        JMenuItem drugayasort = sort.add(new JMenuItem("Убывание названия страны"));

        drugayasort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tableModel.setRowCount(0);
                array = turJornal.sortNameDesc().putTouristJournal();
                for (int i = 0; i < array.length; i++)
                    tableModel.addRow(array[i]);
                table1.setModel(tableModel);

                Box contents = new Box(BoxLayout.Y_AXIS);
                getContentPane().add(contents);
                statusLabel.setText(get_panel_info());
            }
        });

        JMenuItem echedrugayasort = sort.add(new JMenuItem("Убывание шифра маршрута"));

        echedrugayasort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tableModel.setRowCount(0);
                array = turJornal.sortScoreDesc().putTouristJournal();
                for (int i = 0; i < array.length; i++)
                    tableModel.addRow(array[i]);
                table1.setModel(tableModel);

                Box contents = new Box(BoxLayout.Y_AXIS);
                getContentPane().add(contents);
                statusLabel.setText(get_panel_info());
            }
        });

        table.add(function);
        table.add(sort);
    
        JMenuItem delete_condition = function.add(new JMenuItem("Удаление маршрутов меньше заданной цены"));

        delete_condition.addActionListener(new ActionListener() {
            @Override
            
            public void actionPerformed(ActionEvent e) {

                try{
                while(true){
                    String input = JOptionPane.showInputDialog("стоимость маршрута меньше заданной:");
                    if(input.length() > 0){
                        tableModel.setRowCount(0);
                        array = turJornal.delete_condition(Integer.parseInt(input)+1).putTouristJournal();
                        for (int i = 0; i < array.length; i++)
                            tableModel.addRow(array[i]);

                        table1.setModel(tableModel);
                        //table1.setAutoCreateRowSorter(true);
                        Box contents = new Box(BoxLayout.Y_AXIS);
                        getContentPane().add(contents);
                        break;
                    }
                }} catch (Exception exception) {}
                
                statusLabel.setText(get_panel_info());
        }});

        function.add(delete_condition);

        JMenuItem low = function.add(new JMenuItem("Минимум по странам"));

        low.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tableModel.setRowCount(0);
                array = turJornal.sortNameDesc().sortCostDesc().putTouristJournal();
                for (int i = 0; i < array.length; i++)
                    tableModel.addRow(array[i]);

                table1.setModel(tableModel);
                //table1.setAutoCreateRowSorter(true);
                Box contents = new Box(BoxLayout.Y_AXIS);
                getContentPane().add(contents);
                statusLabel.setText(get_panel_info());
            }
        });

        function.add(low);
        
        JMenuItem open = file.add(new JMenuItem("Открыть"));
        open.addActionListener(new ActionListener() { // Действие открытия
            @Override
            public void actionPerformed(ActionEvent e) {

                
                FileDialog fd = new FileDialog(frame, "Открыть", FileDialog.LOAD);
                fd.setVisible(true);
                
                turJornal.deleteAllT();
                String fileName = fd.getFile();
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                turJornal.setName(fileName);
                BufferedReader open = null;
                String[] data = new String[0];
                String line;
                if (fd.getDirectory() != null) {
                    try {
                        open = new BufferedReader(new FileReader(String.format("%s%s", fd.getDirectory(), fd.getFile())));
                        while ((line = open.readLine()) != null) {
                            line = line.trim();
                            if (line.equals(""))
                                continue;
                            data = line.split("\\s+");
                            if (data[0] == " ") {
                                data[0] = "0";
                            }
                            if (data[1] == " ") {
                                data[0] = "0";
                            }
                            if (data[2] == " ") {
                                data[0] = "0";
                            }
                            turJornal.addTourist(
                                    new TouristKey(Integer.parseInt(data[0]), 
                                    data[1], 
                                    Integer.parseInt(data[2])),
                                    Integer.parseInt(data[2]));

                        }
                    } 
                    catch (IOException exception) {
                        exception.printStackTrace();}

                    tableModel.setRowCount(0);
                    array = turJornal.putTouristJournal();
                    for (int i = 0; i < array.length; i++)
                        tableModel.addRow(array[i]);
                    table1.setModel(tableModel);

                    Box contents = new Box(BoxLayout.Y_AXIS);
                    getContentPane().add(contents);
                
                    statusLabel.setText(get_panel_info());
                }
            }
        });

        JMenuItem edit_name = file.add(new JMenuItem("Изменить название"));

        edit_name.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                try {
                    while (true) {
                        String input = JOptionPane.showInputDialog("Новое имя:");
                        if (input.length() > 0) {
                            turJornal.setName(input);
                            break;
                        }
                    }
                } catch (Exception exception) {
                }

                statusLabel.setText(get_panel_info());
            }
        });

        JMenuItem save = file.add(new JMenuItem("Сохранить")); // Кнопка сохранения
        save.addActionListener(new ActionListener() { // Действие сохранения
            @Override
            public void actionPerformed(ActionEvent e) {
                Writer writer = null;
                try {
                
                    JFileChooser fch = new JFileChooser();
                    fch.setDialogTitle("Выбор директории");
                    // Определение режима - только каталог
                    fch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int result = fch.showOpenDialog(GUI.this);
                    
                    if (result == JFileChooser.APPROVE_OPTION){}
                    writer = new BufferedWriter(new FileWriter((String.format("%s%s%s", fch.getSelectedFile()+"\\", turJornal.getName(), ".txt" ))));
                    
                    for (int n = 0; n < tableModel.getRowCount(); n++) { // тут хр. массив [][] из которого берем данны t
                        StringBuilder sb = new StringBuilder();
                        for (int _n = 1; _n < tableModel.getColumnCount(); _n++) {
                            Object data = tableModel.getValueAt(n, _n);
                            if (data == "")
                                sb.append("0");
                            else
                                sb.append(data.toString());
                            sb.append(" ");
                        }
                        writer.append(sb);
                        writer.append("\n");
                    }
                    writer.flush();

                    writer.flush();
                } catch (IOException exception) {
                    exception.printStackTrace();
                } finally {
                    try {
                        if (writer != null)
                            writer.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });



        JMenuItem update = file.add(new JMenuItem("Обновление"));

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String[][] array = new String[tableModel.getRowCount()][tableModel.getColumnCount()];
                turJornal.deleteAllT();
                for (int n = 0; n < tableModel.getRowCount(); n++) { // тут хр. массив [][] из которого берем данны t
                    for (int _n = 0; _n < tableModel.getColumnCount(); _n++) {
                        Object data = tableModel.getValueAt(n, _n);
                        if (data == "")
                            array[n][_n] = ("0");
                        else
                            array[n][_n] = (data.toString());
                    }
                    turJornal.addTourist(new TouristKey(Integer.parseInt(array[n][1]), array[n][2].toString(),
                            Integer.parseInt(array[n][3])), Integer.parseInt(array[n][3]));
                }
                tableModel.setRowCount(0);
                array = turJornal.putTouristJournal();
                for (int i = 0; i < array.length; i++)
                    tableModel.addRow(array[i]);

                tableModel.setRowCount(0);
                array = turJornal.putTouristJournal();
                for (int i = 0; i < array.length; i++)
                    tableModel.addRow(array[i]);
                table1.setModel(tableModel);

                Box contents = new Box(BoxLayout.Y_AXIS);
                getContentPane().add(contents);

                statusLabel.setText(get_panel_info());
                
            }
        });

        table.add(update);
        file.add(edit_name);
        file.addSeparator(); // Разделитель
        JMenuItem exit = file.add(new JMenuItem("Выйти")); // Кнопка выхода

        exit.addActionListener(new ActionListener() { // Действие выхода
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Выход из программы
            }
        });

        // Создание таблицы на основании модели данных
        table1 = new JTable(tableModel);
        //table1.setAutoCreateRowSorter(true);
        // Создание кнопки добавления строки таблицы
        JButton add = new JButton("Добавить");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Номер выделенной строки
                int idx = table1.getSelectedRow();
                // Вставка новой строки после выделенной
                for (int n = idx + 1; n < tableModel.getRowCount(); n++)
                    tableModel.setValueAt(n + 2, n, 0);
                tableModel.insertRow(idx + 1, new String[] { Integer.toString(idx + 2), "", "", "" });
            }
        });
        // Создание кнопки удаления строки таблицы
        JButton remove = new JButton("Удалить");
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Номер выделенной строки
                int idx = table1.getSelectedRow();
                // Удаление выделенной строки
                for (int n = idx + 1; n < tableModel.getRowCount(); n++)
                    tableModel.setValueAt(n, n, 0);
                if (idx >= 0)
                    tableModel.removeRow(idx);
                else{
                    JOptionPane.showMessageDialog(frame, "Выберете строку для удаления", "Ошибка выбора",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        table1.getColumnModel().getColumn(0).setPreferredWidth(3);
        table1.getColumnModel().getColumn(2).setPreferredWidth(100);

        cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table1.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);

        frame.setLayout(new BorderLayout());
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder((Border) new BevelBorder(BevelBorder.LOWERED));
        frame.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        
        // Размещение таблиц в панели с блочным расположением
        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table1));
        getContentPane().add(contents);

        JPanel buttons = new JPanel();
        buttons.add(add);
        buttons.add(remove);
        // Вывод окна на экран
        setSize(500, 500);
        setVisible(true);
        setContentPane(contents);
        getContentPane().add(buttons);
        getContentPane().add(statusLabel);

        setSize(700, 500);
        this.setVisible(true); // Программа видна
        this.setDefaultCloseOperation(GUI.EXIT_ON_CLOSE); // Кнопка выхода из программы (X)
    }

    public static void main(String[] args) {
        new GUI();
    }
}