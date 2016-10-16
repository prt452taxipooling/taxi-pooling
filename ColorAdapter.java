package com.example.taxipooling;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class ColorAdapter extends BaseAdapter {
	
	private List<String> colors;
	private LayoutInflater layoutInflater;
	String color;
	
	public ColorAdapter(Context context) {
		String[] clrs = context.getResources().getStringArray(
				R.array.theme_colors);
		colors = Arrays.asList(clrs);
		layoutInflater = LayoutInflater.from(context);
		color = SharedPreferencesManager.getStringPreferences(
				"theme_color", context, "#ffff00");
	}
	
	@Override
	public int getCount() {
		return colors.size();
	}
	
	@Override
	public String getItem(int position) {
		return colors.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater
					.inflate(R.layout.list_item_color, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.color.setColorFilter(Color.parseColor(getItem(position)),
				Mode.SRC_ATOP);
		if(getItem(position).equals(color))
			holder.selectedColor.setVisibility(View.VISIBLE);
		else
			holder.selectedColor.setVisibility(View.GONE);
		return convertView;
		
	}
	
	private static class ViewHolder {
		private ImageView color;
		private ImageView selectedColor;
		private ViewHolder(View view) {
			color = (ImageView) view.findViewById(R.id.color_image);
			selectedColor = (ImageView) view.findViewById(R.id.selected_color);
		}
	}
	
	public String getColor(int index) {
		return colors.get(index);
	}
}
