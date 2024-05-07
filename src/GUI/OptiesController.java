package GUI;

import domein.DomeinController;

public class OptiesController
    {
    private DomeinController dc;
    private SceneSwitchController ssc;
    private TaalController tc;
    public OptiesController(DomeinController dc, TaalController tc)
        {
        this.dc = dc;
        this.tc = tc;
        this.ssc = new SceneSwitchController(dc);
        }
    }
