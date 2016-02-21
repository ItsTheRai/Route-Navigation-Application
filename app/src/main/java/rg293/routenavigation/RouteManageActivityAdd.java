package rg293.routenavigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class RouteManageActivityAdd extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_manage_add);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openRouteDownloadRoute(View view) {
        Intent intent = new Intent(getApplicationContext(), RouteManageActivityAddDownload.class);
        startActivity(intent);
    }

    public void openRouteCreateRoute(View view) {
        Intent intent = new Intent(getApplicationContext(), RouteManageActivityAddCreate.class);
        startActivity(intent);
    }
}