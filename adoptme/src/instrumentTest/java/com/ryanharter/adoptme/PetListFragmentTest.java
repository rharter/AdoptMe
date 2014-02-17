package com.ryanharter.adoptme;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by rharter on 2/17/14.
 */
public class PetListFragmentTest extends ActivityInstrumentationTestCase2<PetListActivity> {

    private PetListFragment mFragment;

    public PetListFragmentTest() {
        super(PetListActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        // Our Activity won't be created until
        // we call getActivity()
        getActivity();
        mFragment = (PetListFragment) getActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.pet_list);
    }

    public void testPetListFragment_showsHeader() {

    }
}
