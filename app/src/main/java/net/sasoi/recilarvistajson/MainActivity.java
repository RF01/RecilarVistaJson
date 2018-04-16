package net.sasoi.recilarvistajson;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyAdapter mAdapter;
    private ArrayList<User> users;

    private View dialogView;
    private AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclar_view);

        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutmanager);

        //Cargamos la lista de usuarios que enviamos al adapter
        users = loadUsers();
        mAdapter = new MyAdapter(users, this, new IOnUserClick() {
            @Override
            public void onItemClick(User user, int position) {
                //Item de la lista pulsado
                Toast.makeText(MainActivity.this, user.getName(), Toast.LENGTH_SHORT).show();

                //Eliminamos del ArrayList y notificamos al adapter
                users.remove(user);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, users.size());
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        initializeCreationDialog();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showAddDialog();
            }
        });
    }

    private void showAddDialog(){
        if(dialogView.getParent() != null){
            ((ViewGroup) dialogView.getParent()).removeView(dialogView);
        }
        alertDialog.show();
    }

    private void initializeCreationDialog(){
        alertDialog = new AlertDialog.Builder(this);
        dialogView = getLayoutInflater().inflate(R.layout.user_creation_dialog, null);
        alertDialog.setView(dialogView);
        alertDialog.setCancelable(true);

        final EditText name = (EditText) dialogView.findViewById(R.id.edit_name);
        final EditText surname = (EditText) dialogView.findViewById(R.id.edit_surname);
        final EditText age = (EditText) dialogView.findViewById(R.id.edit_age);

        alertDialog.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                //Cramos un nuevo usuario
                User user = new User();
                user.setName(name.getText().toString());
                user.setSurname(surname.getText().toString());
                user.setAge(Integer.parseInt(age.getText().toString()));

               // Añadimos el usuario al ArrayList y notificamos al adapter
               users.add(user);
               mAdapter.notifyItemInserted(users.size() -1);

            }
        });
        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
    }

    private ArrayList<User> loadUsers(){
        ArrayList<User> users = new ArrayList<>();
        User user1 = new User("Juan", "Fernadez", 78);
        User user2 = new User("Ibai", "Ligero", 4);
        User user3 = new User("Jose", "Rodrigez", 40);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }


}
