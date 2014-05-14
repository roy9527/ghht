/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.senab.actionbarpulltorefresh.samples.stock;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ghtt.activity.MainCardActivity.GoogleCardsAdapter;
import com.ghtt.fortao.R;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

/**
 * This sample shows how to use ActionBar-PullToRefresh with a
 * {@link android.widget.ListView ListView}, and manually creating (and
 * attaching) a {@link PullToRefreshLayout} to the view.
 */
public class ListViewActivity extends BaseSampleActivity {

	@Override
	protected Fragment getSampleFragment() {
		return new SampleListFragment();
	}

	/**
	 * Fragment Class
	 */
	public static class SampleListFragment extends ListFragment implements
			OnRefreshListener {

		private static String[] ITEMS = { "Abbaye de Belloc",
				"Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
				"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu",
				"Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler",
				"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
				"Abondance", "Ackawi", "Acorn", "Adelost",
				"Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
				"Aisy Cendre", "Allgauer Emmentaler" };

		private PullToRefreshLayout mPullToRefreshLayout;

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			ViewGroup viewGroup = (ViewGroup) view;

			// As we're using a ListFragment we create a PullToRefreshLayout
			// manually
			mPullToRefreshLayout = new PullToRefreshLayout(
					viewGroup.getContext());

			// We can now setup the PullToRefreshLayout
			ActionBarPullToRefresh
					.from(getActivity())
					// We need to insert the PullToRefreshLayout into the
					// Fragment's ViewGroup
					.insertLayoutInto(viewGroup)
					// Here we mark just the ListView and it's Empty View as
					// pullable
					.theseChildrenArePullable(android.R.id.list,
							android.R.id.empty).listener(this)
					.setup(mPullToRefreshLayout);
		}

		private ArrayList<Integer> getItems() {
			ArrayList<Integer> items = new ArrayList<Integer>();
			for (int i = 0; i < 100; i++) {
				items.add(i);
			}
			return items;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			ListView listView = getListView();

			final GoogleCardsAdapter mGoogleCardsAdapter = new GoogleCardsAdapter(
					getActivity());
			SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
					mGoogleCardsAdapter /*new SwipeDismissAdapter(mGoogleCardsAdapter,
							new OnDismissCallback() {

								@Override
								public void onDismiss(AbsListView listView,
										int[] reverseSortedPositions) {
									for (int position : reverseSortedPositions) {
										mGoogleCardsAdapter.remove(position);
									}
								}
							})*/);
			swingBottomInAnimationAdapter.setInitialDelayMillis(300);
			swingBottomInAnimationAdapter.setAnimationDurationMillis(1000);
			swingBottomInAnimationAdapter.setAbsListView(listView);

			listView.setAdapter(swingBottomInAnimationAdapter);

			mGoogleCardsAdapter.addAll(getItems());

			// Set the List Adapter to display the sample items
			/*
			 * setListAdapter(new ArrayAdapter<String>(getActivity(),
			 * android.R.layout.simple_list_item_1, ITEMS));
			 * setListShownNoAnimation(true);
			 */
			setListShownNoAnimation(true);
		}

		@Override
		public void onRefreshStarted(View view) {
			// Hide the list
			setListShown(false);

			/**
			 * Simulate Refresh with 4 seconds sleep
			 */
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					try {
						Thread.sleep(Constants.SIMULATED_REFRESH_LENGTH);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);

					// Notify PullToRefreshLayout that the refresh has finished
					mPullToRefreshLayout.setRefreshComplete();

					if (getView() != null) {
						// Show the list again
						setListShown(true);
					}
				}
			}.execute();
		}
	}
}
