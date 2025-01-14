/*
 * This file is part of Grocy Android.
 *
 * Grocy Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Grocy Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Grocy Android. If not, see http://www.gnu.org/licenses/.
 *
 * Copyright (c) 2020-2021 by Patrick Zedler and Dominic Zedler
 */

package xyz.zedler.patrick.grocy.fragment.bottomSheetDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import xyz.zedler.patrick.grocy.R;
import xyz.zedler.patrick.grocy.activity.MainActivity;
import xyz.zedler.patrick.grocy.fragment.BaseFragment;
import xyz.zedler.patrick.grocy.model.Location;
import xyz.zedler.patrick.grocy.util.Constants;
import xyz.zedler.patrick.grocy.view.ListItem;

public class MasterLocationBottomSheet extends BaseBottomSheet {

  private final static String TAG = MasterLocationBottomSheet.class.getSimpleName();

  private MainActivity activity;
  private Location location;
  private ListItem itemName, itemDescription;

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return new BottomSheetDialog(requireContext(), R.style.Theme_Grocy_BottomSheetDialog);
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState
  ) {
    View view = inflater.inflate(
        R.layout.fragment_bottomsheet_master_location,
        container,
        false
    );

    activity = (MainActivity) getActivity();
    assert activity != null;

    Bundle bundle = getArguments();
    if (bundle != null) {
      location = bundle.getParcelable(Constants.ARGUMENT.LOCATION);
    }

    // VIEWS

    itemName = view.findViewById(R.id.item_master_location_name);
    itemDescription = view.findViewById(R.id.item_master_location_description);

    // TOOLBAR

    MaterialToolbar toolbar = view.findViewById(R.id.toolbar_master_location);
    toolbar.setOnMenuItemClickListener(item -> {
      BaseFragment fragmentCurrent = activity.getCurrentFragment();
      if (item.getItemId() == R.id.action_edit) {
        fragmentCurrent.editObject(location);
      } else if (item.getItemId() == R.id.action_delete) {
        fragmentCurrent.deleteObjectSafely(location);
      }
      dismiss();
      return true;
    });

    setData();

    return view;
  }

  private void setData() {
    // NAME
    itemName.setText(activity.getString(R.string.property_name), location.getName());

    // DESCRIPTION
    String description = location.getDescription();
    if (description != null && !description.isEmpty()) {
      itemDescription.setSingleLine(false);
      itemDescription.setText(activity.getString(R.string.property_description), description);
    } else {
      itemDescription.setVisibility(View.GONE);
    }
  }

  @NonNull
  @Override
  public String toString() {
    return TAG;
  }
}
