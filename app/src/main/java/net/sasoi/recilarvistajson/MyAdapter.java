package net.sasoi.recilarvistajson;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.ViewHolder>{

    private static ArrayList<User> users;
    private IOnUserClick caller;

    //Constructor donde recogemso la info
    public MyAdapter(ArrayList<User> users, Context context, IOnUserClick caller){
        this.users = users;
        this.caller = caller;
    }



    //Referencia a la vista de cada uno
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView surnameTextView;
        public ImageView ageImageView;

        public ViewHolder (View itemView){
            super (itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_textView);
            surnameTextView = (TextView) itemView.findViewById(R.id.surname_textView);
            ageImageView = (ImageView) itemView.findViewById(R.id.age_imageView);
        }

        // Metodo para detecta al pulsaci´n sobre elitem
        void bind(final User user, final int position, final IOnUserClick caller){
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View view){
                    //notificamos el item pasado
                    caller.onItemClick(user,position);
                }
            });
        }

    }

    //Creación de nueva vistas utilizando el ViewHolder superior
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);

        return new ViewHolder(view);
    }

    //sobreescribimos la vista con el contaenido del elemento
    @Override
    public void onBindViewHolder( ViewHolder holder, int position){
        User user = users.get(position);

        //preparamos el metodo apara dectectar la pulsación
        holder.bind(user, position, caller);

        holder.nameTextView.setText(user.getName());
        holder.surnameTextView.setText(user.getSurname());

        //Recogemos la edad y en fucnión de ella cargamso una u otra imagen
        int age = user.getAge();
        if (age <= 15 ){
            holder.ageImageView.setImageResource(R.drawable.son);

        }else if (age <= 70){
            holder.ageImageView.setImageResource(R.drawable.man);
        }else {
            holder.ageImageView.setImageResource(R.drawable.grandfather);
        }
    }

    //Numero de elementos añadidos
    @Override
    public int getItemCount(){
        return users.size();
    }

}
