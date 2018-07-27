package org.kimrong.dict.ui.parts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.kimrong.dict.ChineseOfWord;
import org.kimrong.dict.Resources;
import org.kimrong.dict.Words;

public class SamplePart {

    private StyledText sourceInput;
    // private StyledText chineseWordInput;
    private StyledText noChineseWordInput;
    // private TableViewer tableViewer;

    // @Inject
    // private MDirtyable dirty;

    @PostConstruct
    public void createComposite(Composite parent) {
        // parent.setLayout(new GridLayout(1, false));
        SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
        GridLayoutFactory.fillDefaults().applyTo(parent);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(sashForm);

        sourceInput = createStyledText(sashForm);
        // sourceInput.setText("Enter text to mark part as dirty");
        // sourceInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // chineseWordInput = createStyledText(sashForm);
        // chineseWordInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        noChineseWordInput = createStyledText(sashForm);
        noChineseWordInput.setEditable(false);
        // noChineseWordInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        sashForm.setWeights(new int[] { 50, 50 });

        sourceInput.addModifyListener(arg0 -> {

            refresh();

        });
    }

    private void refresh() {
        // String resourceUrl = Resources.ROOT + "resource\\";
        Map<String, String> chineseOfWord = new HashMap<>();
        ChineseOfWord.getChineseOfWord(Resources.ROOT + "chineseOfWord\\", chineseOfWord);
        ChineseOfWord.getChineseOfWord(Resources.ROOT + "correctChineseOfWord\\", chineseOfWord);

        String text = sourceInput.getText();
        Collection<String> words = Words.pickEnglishWordsByPhrase(text, chineseOfWord);
        Collection<String> ingoreWords = Words.words(Resources.ROOT + "ignoreWords\\");
        words.removeAll(ingoreWords);

        Map<String, String> chineseWord = new TreeMap<>();
        Set<String> noChineseWord = new TreeSet<>();

        Map<String, String> rootOfWords = ChineseOfWord.getChineseOfWord(Resources.ROOT + "rootOfWord\\");

        for (String word : words) {
            String value = chineseOfWord.get(word);
            if (value == null) {
                noChineseWord.add(word);
            } else {
                if (!word.toLowerCase().equals(value.toLowerCase())) {
                    // word == value 是因为没有获得翻译。

                    String roots = rootOfWords.get(word);
                    if (roots == null) {
                        chineseWord.put(word, value);
                    } else {
                        String[] splits = roots.split("[^a-zA-Z]+");
                        StringBuilder sb = new StringBuilder();
                        sb.append(value).append(" ");
                        for (String split : splits) {
                            sb.append(split).append(" ");
                            String value2 = chineseOfWord.get(split);
                            if (value2 != null) {
                                sb.append(value2).append(" ");
                            }
                        }
                        chineseWord.put(word, sb.toString());
                    }
                }
            }
        }

        noChineseWordInput.setBackground(noChineseWordInput.getDisplay().getSystemColor(noChineseWord.isEmpty() ? SWT.COLOR_GREEN : SWT.COLOR_RED));

        if (noChineseWord.isEmpty()) {
            StringBuilder chineseBuffer = new StringBuilder();
            chineseWord.forEach((key, value) -> chineseBuffer.append(key).append(" ").append(value).append(".\n"));
            chineseBuffer.append("---------------------------------------\n");
            chineseBuffer.append(text);
            noChineseWordInput.setText(chineseBuffer.toString());
        } else {
            StringBuilder noChineseBuffer = new StringBuilder();
            noChineseWord.forEach(w -> noChineseBuffer.append(w).append("\n"));
            String onChineseValue = noChineseBuffer.toString();
            noChineseWordInput.setText(onChineseValue);
        }
    }

    private StyledText createStyledText(Composite sashForm) {
        StyledText styledText = new StyledText(sashForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
        styledText.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if ((keyEvent.stateMask == SWT.CTRL) && (keyEvent.character == ('A' - 64))) {
                    styledText.selectAll();
                }
                // F5
                if (keyEvent.keyCode == SWT.F5) {
                    refresh();
                }
            }
        });

        return styledText;

    }

    @Focus
    public void setFocus() {
        // tableViewer.getTable().setFocus();
    }

    @Persist
    public void save() {
        // dirty.setDirty(false);
    }

    // private List<String> createInitialDataModel() {
    // return Arrays.asList("Sample item 1", "Sample item 2", "Sample item 3", "Sample item 4", "Sample item 5");
    // }
}