package apatti.android.games.thanni;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.Bid;
import model.Card;
import model.DisplayCardListener;
import model.DisplayMessageListener;
import model.Game;
import model.RequestUserCardListener;
import model.TDeck;

public class GameActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new GameFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class GameFragment extends Fragment implements DisplayCardListener, DisplayMessageListener, RequestUserCardListener {

        Game game;
        TDeck deck;
        private Bid userBid;

        @Override
        public void onDisplayUserCard(Card card) {
            playCard(getView(),card.getResourceId());
        }

        @Override
        public void onDisplayTrumpCard(int playerId) {
            ((ImageView)getView().findViewById(R.id.imgViewTrump)).setImageResource(game.getCurrentRound().getRoundTrumpCard().getResourceId());
        }

        @Override
        public void onDisplayMessage(String message) {
            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestUserCard() {
            Toast.makeText(getActivity(),"Play the card",Toast.LENGTH_SHORT).show();
        }

        private final class TrumpCardDragListener implements View.OnDragListener{
            Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
            Drawable normalShape = getResources().getDrawable(R.drawable.shape);
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                if(v.findViewById(R.id.imgViewTrump)==null)
                    return false;
                switch (action)
                {
                    case DragEvent.ACTION_DRAG_STARTED:
                        if(event.getClipDescription().getLabel().equals("playCard")) {
                            ((ImageView)v).setAlpha((float)0.7);
                            v.invalidate();
                            return true;
                        }
                        return false;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        ((ImageView)v).setAlpha((float)0.2);
                        v.invalidate();
                        v.setBackgroundDrawable(enterShape);
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        ((ImageView)v).setAlpha((float)0.7);
                        v.invalidate();
                        v.setBackgroundDrawable(normalShape);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        ((ImageView)v).setAlpha((float)1);
                        v.invalidate();
                        v.setBackgroundDrawable(normalShape);
                        return true;
                    case DragEvent.ACTION_DROP:
                        View view = (View)event.getLocalState();
                        ((ImageView)v).setImageResource(R.drawable.b1fv);
                        ((ImageView)v).setAlpha((float) 1);
                        ((ImageView)v).setOnDragListener(null);
                        Toast.makeText(getActivity(),((Card)((ImageView)view).getTag()).getFaceValue(),Toast.LENGTH_SHORT).show();
                        game.getPlayers().get(3).setTrump((Card) ((ImageView) view).getTag());
                        game.getCurrentRound().setRoundTrumpCard(game.getPlayers().get(3).getTrump());
                        displayUserCards(v.getRootView());
                        setBidControlVisibility(v.getRootView(),View.GONE);
                        playRound(v.getRootView());
                        v.invalidate();
                        return  true;
                    default:
                        break;
                }
                return false;
            }
        }
        private final class PlayedCardsDragListener implements View.OnDragListener{
            Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
            Drawable normalShape = getResources().getDrawable(R.drawable.shape);
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                if(v.findViewById(R.id.imgViewPlayedCard4)==null)
                    return false;
                switch (action)
                {
                    case DragEvent.ACTION_DRAG_STARTED:
                        if(event.getClipDescription().getLabel().equals("playCard")) {
                            //((ImageView) v.findViewById(R.id.imgViewPlayedCard4)).setColorFilter(Color.BLUE);
                            ((ImageView)v).setAlpha((float)0.7);
                            v.invalidate();
                            return true;
                        }
                        return false;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        //((ImageView)v.findViewById(R.id.imgViewPlayedCard4)).setColorFilter(Color.GREEN);
                        ((ImageView)v).setAlpha((float)0.2);
                        v.invalidate();
                        v.setBackgroundDrawable(enterShape);
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        //((ImageView)v.findViewById(R.id.imgViewPlayedCard4)).setColorFilter(Color.BLUE);
                        ((ImageView)v).setAlpha((float)0.7);
                        v.invalidate();
                        v.setBackgroundDrawable(normalShape);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //((ImageView)v.findViewById(R.id.imgViewPlayedCard4)).clearColorFilter();
                        ((ImageView)v).setAlpha((float)1);
                        v.invalidate();
                        v.setBackgroundDrawable(normalShape);
                        return true;
                    case DragEvent.ACTION_DROP:
                        View view = (View)event.getLocalState();
                        //ViewGroup owner = (ViewGroup) view.getParent();
                        //owner.removeView(view);
                        Card c = (Card)((ImageView)view).getTag();
                        playCard(v.getRootView(),c.getResourceId());
                        game.getPlayers().get(3).getHand().playCard(c.getId());
                        displayUserCards(v.getRootView());
                        ((ImageView)v.findViewById(R.id.imgViewPlayedCard4)).clearColorFilter();
                        v.invalidate();
                        game.playCard(0);
                        return  true;
                    default:
                        break;
                }
                return false;
            }
        }

        private final class SwapCardsDragListener implements View.OnDragListener{
            Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
            Drawable normalShape = getResources().getDrawable(R.drawable.shape);
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                switch (action)
                {
                    case DragEvent.ACTION_DRAG_STARTED:
                        if(event.getClipDescription().getLabel().equals("playCard")) {
                            //((ImageView) v).setColorFilter(Color.BLUE);
                            v.invalidate();
                            return true;
                        }
                        return false;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        //((ImageView)v).setColorFilter(Color.GREEN);
                        ((ImageView)v).setAlpha((float)0.2);
                        v.invalidate();
                        v.setBackgroundDrawable(enterShape);
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        ((ImageView)v).setAlpha((float) 1);
                        v.invalidate();
                        v.setBackgroundDrawable(normalShape);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        ((ImageView)v).setAlpha((float) 1);
                        v.invalidate();
                        v.setBackgroundDrawable(normalShape);
                        return true;
                    case DragEvent.ACTION_DROP:
                        View view = (View)event.getLocalState();
                        ImageView dragFromCard = ((ImageView)view);
                        ImageView dragToCard = ((ImageView)v);

                        moveCard((Card)dragFromCard.getTag(),(Card)dragToCard.getTag());

                        displayUserCards(v.getRootView());
                        v.invalidate();
                        return  true;
                    default:
                        break;
                }
                return false;
            }
        }

        private final class UserCardsTouchListener implements View.OnTouchListener {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    ClipData dragData = ClipData.newPlainText("playCard","");
                    view.startDrag(dragData, shadowBuilder, view, 0);
                    //view.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        }

        private void playCard(View v, int resourceId)
        {
            if(((ImageView)v.findViewById(R.id.imgViewPlayedCard1)).getTag()=="") {
                ((ImageView) v.findViewById(R.id.imgViewPlayedCard1)).setImageResource(resourceId);
                ((ImageView) v.findViewById(R.id.imgViewPlayedCard1)).setTag(resourceId);
                return;
            }
            if(((ImageView)v.findViewById(R.id.imgViewPlayedCard2)).getTag()=="") {
                ((ImageView) v.findViewById(R.id.imgViewPlayedCard2)).setImageResource(resourceId);
                ((ImageView) v.findViewById(R.id.imgViewPlayedCard2)).setTag(resourceId);
                return;
            }
            if(((ImageView)v.findViewById(R.id.imgViewPlayedCard3)).getTag()=="") {
                ((ImageView) v.findViewById(R.id.imgViewPlayedCard3)).setImageResource(resourceId);
                ((ImageView) v.findViewById(R.id.imgViewPlayedCard3)).setTag(resourceId);
                return;
            }
            if(((ImageView)v.findViewById(R.id.imgViewPlayedCard4)).getTag()=="") {
                ((ImageView) v.findViewById(R.id.imgViewPlayedCard4)).setImageResource(resourceId);
                ((ImageView) v.findViewById(R.id.imgViewPlayedCard4)).setTag(resourceId);
                return;
            }
        }

        private void moveCard(Card card,Card cardToReplace)
        {
            game.getPlayers().get(3).getHand().moveCard(card, cardToReplace);
        }

        public GameFragment() {
        }

        private void setBidControlVisibility(View rootView, int visibility)
        {
            if(visibility==View.VISIBLE) {
                switch (game.getCurrentRound().getRoundTarget()) {
                    case JOHN:
                        (rootView.findViewById(R.id.btnBidJohn)).setEnabled(false);
                    case NINETY:
                        (rootView.findViewById(R.id.btnBid90)).setEnabled(false);
                    case EIGHTY:
                        (rootView.findViewById(R.id.btnBid80)).setEnabled(false);
                    case SEVENTY:
                        (rootView.findViewById(R.id.btnBid70)).setEnabled(false);
                    case SIXTY:
                        (rootView.findViewById(R.id.btnBid60)).setEnabled(false);
                    case BEAT:
                        (rootView.findViewById(R.id.btnBidBeat)).setEnabled(false);
                }
            }
            ((LinearLayout)rootView.findViewById(R.id.llBidControl)).setVisibility(visibility);
            //this.getActivity().findViewById(R.id.llBidControl).setVisibility(visibility);
        }

        private void freePlayingCard(View v)
        {
            ((ImageView)v.findViewById(R.id.imgViewPlayedCard1)).setTag("");
            ((ImageView)v.findViewById(R.id.imgViewPlayedCard2)).setTag("");
            ((ImageView)v.findViewById(R.id.imgViewPlayedCard3)).setTag("");
            ((ImageView)v.findViewById(R.id.imgViewPlayedCard4)).setTag("");
        }

        private void displayUserCards(View rootView)
        {
            ArrayList<ImageView> imgViewPlayerList = new ArrayList<ImageView>(6);

            imgViewPlayerList.add((ImageView)rootView.findViewById(R.id.imgViewUpC1));
            imgViewPlayerList.add((ImageView)rootView.findViewById(R.id.imgViewUpC2));
            imgViewPlayerList.add((ImageView)rootView.findViewById(R.id.imgViewUpC3));
            imgViewPlayerList.add((ImageView)rootView.findViewById(R.id.imgViewUpC4));
            imgViewPlayerList.add((ImageView)rootView.findViewById(R.id.imgViewUpC5));
            imgViewPlayerList.add((ImageView)rootView.findViewById(R.id.imgViewUpC6));
            for (ImageView imgView : imgViewPlayerList)
            {
                imgView.setOnTouchListener(new UserCardsTouchListener());
                imgView.setOnDragListener(new SwapCardsDragListener());
                imgView.setVisibility(View.GONE);
            }
            int index=0;
            for(Card c : game.getPlayers().get(3).getHand().getCards())
            {
                imgViewPlayerList.get(index).setImageResource(c.getResourceId());
                imgViewPlayerList.get(index).setTag(c);
                imgViewPlayerList.get(index).setVisibility(View.VISIBLE);
                index++;
            }
        }

        private void gameSetUp(View rootView)
        {

            int nameIndex = (new Random()).nextInt(3);
            String topPlayer="";
            String leftPlayer="";
            String rightPlayer="";
            if(nameIndex==0)
            {
                topPlayer = getResources().getString(R.string.player1_name);
                leftPlayer = getResources().getString(R.string.player2_name);
                rightPlayer = getResources().getString(R.string.player3_name);
            }
            if(nameIndex==1)
            {
                topPlayer = getResources().getString(R.string.player2_name);
                leftPlayer = getResources().getString(R.string.player3_name);
                rightPlayer = getResources().getString(R.string.player1_name);
            }
            if(nameIndex==2)
            {
                topPlayer = getResources().getString(R.string.player3_name);
                leftPlayer = getResources().getString(R.string.player1_name);
                rightPlayer = getResources().getString(R.string.player2_name);
            }
            ArrayList<String> playerNames = new ArrayList<String>();
            playerNames.add(leftPlayer);
            playerNames.add(topPlayer);
            playerNames.add(rightPlayer);
            playerNames.add("User");
            game = new Game(playerNames,this,this,this);

            ((TextView)rootView.findViewById(R.id.txtViewTopPlayerName)).setText(topPlayer);
            ((TextView)rootView.findViewById(R.id.txtViewLeftPlayerName)).setText(leftPlayer);
            ((TextView)rootView.findViewById(R.id.txtViewRightPlayerName)).setText(rightPlayer);

            ((Button)rootView.findViewById(R.id.btnBidBeat)).setOnClickListener(bidButtonClickListenser);
            ((Button)rootView.findViewById(R.id.btnBidBeat)).setTag(Bid.BEAT);
            ((Button)rootView.findViewById(R.id.btnBidBeat)).setEnabled(true);
            ((Button)rootView.findViewById(R.id.btnBid60)).setOnClickListener(bidButtonClickListenser);
            ((Button)rootView.findViewById(R.id.btnBid60)).setTag(Bid.SIXTY);
            ((Button)rootView.findViewById(R.id.btnBid60)).setEnabled(true);
            ((Button)rootView.findViewById(R.id.btnBid70)).setOnClickListener(bidButtonClickListenser);
            ((Button)rootView.findViewById(R.id.btnBid70)).setTag(Bid.SEVENTY);
            ((Button)rootView.findViewById(R.id.btnBid70)).setEnabled(true);
            ((Button)rootView.findViewById(R.id.btnBid80)).setOnClickListener(bidButtonClickListenser);
            ((Button)rootView.findViewById(R.id.btnBid80)).setTag(Bid.EIGHTY);
            ((Button)rootView.findViewById(R.id.btnBid80)).setEnabled(true);
            ((Button)rootView.findViewById(R.id.btnBid90)).setOnClickListener(bidButtonClickListenser);
            ((Button)rootView.findViewById(R.id.btnBid90)).setTag(Bid.NINETY);
            ((Button)rootView.findViewById(R.id.btnBid90)).setEnabled(true);
            ((Button)rootView.findViewById(R.id.btnBidJohn)).setOnClickListener(bidButtonClickListenser);
            ((Button)rootView.findViewById(R.id.btnBidJohn)).setTag(Bid.JOHN);
            ((Button)rootView.findViewById(R.id.btnBidJohn)).setEnabled(true);
            ((Button)rootView.findViewById(R.id.btnBidPass)).setOnClickListener(bidButtonClickListenser);
            ((Button)rootView.findViewById(R.id.btnBidPass)).setTag(Bid.PASS);
            ((Button)rootView.findViewById(R.id.btnBidPass)).setEnabled(true);
            ((TextView)rootView.findViewById(R.id.txtViewScore)).setText(getResources().getString(R.string.scoreTxt));
        }

        View.OnClickListener bidButtonClickListenser = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBid = (Bid)v.getTag();
                Toast.makeText(getActivity(),"Bid",Toast.LENGTH_SHORT).show();
                game.getCurrentRound().getRoundBidding().set(3, (Bid) v.getTag());
                if(userBid!=Bid.PASS)
                    game.getCurrentRound().setRoundTarget(userBid);
                else {
                    Toast.makeText(getActivity(),"PASS",Toast.LENGTH_SHORT).show();
                    setBidControlVisibility(v.getRootView(), View.GONE);
                    Toast.makeText(getActivity(),"GONE ",Toast.LENGTH_SHORT).show();
                }
                bidProcess(v.getRootView(),true);
            }
        };

        private void roundSetUp(View rootView)
        {

            game.startNewRound();
            deck = new TDeck("");
            for(int i=0;i<52;i++)
            {
                deck.setCardResourceId(i,getResources().getIdentifier("c"+(i+1),"drawable","apatti.android.games.thanni"));
            }
            deck.shuffle();
            //Start of round
            ImageView imgViewTpC1 = ((ImageView)rootView.findViewById(R.id.imgViewTpC1));
            ImageView imgViewTpC2 = ((ImageView)rootView.findViewById(R.id.imgViewTpC2));
            ImageView imgViewTpC3 = ((ImageView)rootView.findViewById(R.id.imgViewTpC3));
            ImageView imgViewTpC4 = ((ImageView)rootView.findViewById(R.id.imgViewTpC4));
            ImageView imgViewTpC5 = ((ImageView)rootView.findViewById(R.id.imgViewTpC5));
            ImageView imgViewTpC6 = ((ImageView)rootView.findViewById(R.id.imgViewTpC6));
            ImageView imgViewLpC1 = ((ImageView)rootView.findViewById(R.id.imgViewLpC1));
            ImageView imgViewLpC2 = ((ImageView)rootView.findViewById(R.id.imgViewLpC2));
            ImageView imgViewLpC3 = ((ImageView)rootView.findViewById(R.id.imgViewLpC3));
            ImageView imgViewLpC4 = ((ImageView)rootView.findViewById(R.id.imgViewLpC4));
            ImageView imgViewLpC5 = ((ImageView)rootView.findViewById(R.id.imgViewLpC5));
            ImageView imgViewLpC6 = ((ImageView)rootView.findViewById(R.id.imgViewLpC6));
            ImageView imgViewRpC1 = ((ImageView)rootView.findViewById(R.id.imgViewRpC1));
            ImageView imgViewRpC2 = ((ImageView)rootView.findViewById(R.id.imgViewRpC2));
            ImageView imgViewRpC3 = ((ImageView)rootView.findViewById(R.id.imgViewRpC3));
            ImageView imgViewRpC4 = ((ImageView)rootView.findViewById(R.id.imgViewRpC4));
            ImageView imgViewRpC5 = ((ImageView)rootView.findViewById(R.id.imgViewRpC5));
            ImageView imgViewRpC6 = ((ImageView)rootView.findViewById(R.id.imgViewRpC6));

            ((ImageView)rootView.findViewById(R.id.imgViewTrump)).setImageResource(R.drawable.c0);
            ((ImageView)rootView.findViewById(R.id.imgViewPlayedCard4)).setOnDragListener(new PlayedCardsDragListener());
            ((ImageView)rootView.findViewById(R.id.imgViewPlayedCard4)).setEnabled(false);
            freePlayingCard(rootView);

            imgViewLpC1.setVisibility(View.VISIBLE);
            imgViewLpC2.setVisibility(View.VISIBLE);
            imgViewLpC3.setVisibility(View.VISIBLE);
            imgViewLpC4.setVisibility(View.VISIBLE);
            imgViewRpC1.setVisibility(View.VISIBLE);
            imgViewRpC2.setVisibility(View.VISIBLE);
            imgViewRpC3.setVisibility(View.VISIBLE);
            imgViewRpC4.setVisibility(View.VISIBLE);
            imgViewTpC1.setVisibility(View.VISIBLE);
            imgViewTpC2.setVisibility(View.VISIBLE);
            imgViewTpC3.setVisibility(View.VISIBLE);
            imgViewTpC4.setVisibility(View.VISIBLE);

            int dealerId=game.getDealerId();
            ((TextView)rootView.findViewById(R.id.txtViewLeftPlayerDealer)).setVisibility(View.GONE);
            ((TextView)rootView.findViewById(R.id.txtViewRightPlayerDealer)).setVisibility(View.GONE);
            ((TextView)rootView.findViewById(R.id.txtViewTopPlayerDealer)).setVisibility(View.GONE);
            switch(dealerId)
            {
                case 0:
                    ((TextView)rootView.findViewById(R.id.txtViewLeftPlayerDealer)).setVisibility(View.VISIBLE);
                    break;
                case 1:
                    ((TextView)rootView.findViewById(R.id.txtViewTopPlayerDealer)).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    ((TextView)rootView.findViewById(R.id.txtViewRightPlayerDealer)).setVisibility(View.VISIBLE);
                    break;
                case 3:
                    break;
            }
            ((ImageView)rootView.findViewById(R.id.imgViewUWin)).setVisibility(View.INVISIBLE);
            ((ImageView)rootView.findViewById(R.id.imgViewOWin)).setVisibility(View.INVISIBLE);
            //((ImageView)rootView.findViewById(R.id.imgViewTrump)).setVisibility(View.INVISIBLE);
            ((ImageView)rootView.findViewById(R.id.imgViewTrump)).setOnClickListener(TrumpClickListener);

            ((TextView)rootView.findViewById(R.id.txtViewTrump)).setText(getResources().getString(R.string.trumpTxt));
            ((TextView)rootView.findViewById(R.id.txtViewTrumpTeam)).setText(getResources().getString(R.string.trumpTeamTxt));
            ((TextView)rootView.findViewById(R.id.txtViewRoundTarget)).setText(getResources().getString(R.string.roundTargetTxt));

        }

        View.OnClickListener TrumpClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView)v).setImageResource(game.getCurrentRound().getRoundTrumpCard().getResourceId());
                ((ImageView)v).setVisibility(View.VISIBLE);
            }
        };
        private void dealCards(int number)
        {
            int dealerId=game.getDealerId();
            for(int i=0;i<4;i++)
            {
                for(int j=0;j<number;j++)
                {
                    game.getPlayers().get(dealerId).getHand().addCard(deck.getCard());
                }
                dealerId=(dealerId+1)%4;
            }
        }

        private void bidProcess(View rootView,boolean userCompleted)
        {
            int startIndex=(game.getDealerId()+1)%4;
            if(userCompleted)
                startIndex=0;
            int j=0;
            int i=0;
            for(i=startIndex;j<4;i=(i+1)%4)
            {
                if(game.getCurrentRound().getRoundBidding().get(i)==Bid.PASS)
                {
                    j++;
                    continue;
                }
                if(game.getCurrentRound().getRoundTarget()==game.getCurrentRound().getRoundBidding().get(i))
                {
                    break;
                }
                else
                {
                    if(i==3) //user
                    {
                        setBidControlVisibility(rootView,View.VISIBLE);
                        return;
                    }
                    int teamId=(i+2)%4;
                    Bid b = game.getPlayers().get(i).bid(game.getCurrentRound().getRoundTarget(),game.getCurrentRound().getRoundBidding().get(teamId),0);
                    game.getCurrentRound().getRoundBidding().set(i,b);
                    showBid(rootView,i,b);
                    if(b!=Bid.PASS)
                    {
                        game.getCurrentRound().setRoundTarget(b);
                        j=0;
                    }
                    else
                        j++;
                }
            }
            if(j==4)
            {
                int playerId=(game.getDealerId()+1)%4;
                game.getCurrentRound().getRoundBidding().set(playerId,Bid.BEAT);
                game.getCurrentRound().setRoundTarget(Bid.BEAT);
                game.getCurrentRound().setRoundTrumpTeam(playerId);
                if(playerId==3) {
                    Toast.makeText(getActivity(),"Please select trump card",Toast.LENGTH_LONG).show();
                    ((ImageView)rootView.findViewById(R.id.imgViewTrump)).setOnDragListener(new TrumpCardDragListener());
                    return;
                }
                showBid(rootView, playerId, Bid.BEAT);
                game.getCurrentRound().setRoundTrumpCard(game.getPlayers().get(playerId).getTrump());
            }
            else
            {
                game.getCurrentRound().setRoundTrumpTeam(i);
                if(i==3)
                {
                    Toast.makeText(getActivity(),"Please select trump card",Toast.LENGTH_LONG).show();
                    ((ImageView)rootView.findViewById(R.id.imgViewTrump)).setOnDragListener(new TrumpCardDragListener());
                    return;
                }
                game.getCurrentRound().setRoundTrumpCard(game.getPlayers().get(i).getTrump());
            }
            ((TextView)rootView.findViewById(R.id.txtViewTrumpTeam)).setText(getResources().getString(R.string.trumpTeamTxt)+game.getPlayers().get(i).getName());
            ((TextView)rootView.findViewById(R.id.txtViewRoundTarget)).setText(getResources().getString(R.string.roundTargetTxt)+game.getCurrentRound().getRoundTarget().getValue());
            ((ImageView)rootView.findViewById(R.id.imgViewTrump)).setImageResource(R.drawable.b1fv);

            playRound(rootView);
            return;
        }

        public void showBid(View rootView,int playerId,Bid b)
        {
            switch (playerId)
            {
                case 0:
                    ((TextView)(rootView.findViewById(R.id.txtViewLeftPlayerScore))).setText(b.toString());
                    break;
                case 1:
                    ((TextView)(rootView.findViewById(R.id.txtViewTopPlayerScore))).setText(b.toString());
                    break;
                case 2:
                    ((TextView)(rootView.findViewById(R.id.txtViewRightPlayerScore))).setText(b.toString());
                    break;
            }
        }

        private String getTeamName(int id)
        {
            return game.getPlayers().get(id).getName()+"/"+game.getPlayers().get(id%2).getName();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_game, container, false);
            //Initial set up.
            gameSetUp(rootView);
            roundSetUp(rootView);
            dealCards(4);
            Collections.sort(game.getPlayers().get(3).getHand().getCards());
            displayUserCards(rootView);
            bidProcess(rootView,false);
            //backgroundView.setImageURI();
            return rootView;
        }

        private void playRound(View rootView)
        {
            dealCards(2);
            displayUserCards(rootView);
            ((ImageView)rootView.findViewById(R.id.imgViewPlayedCard4)).setEnabled(true);
            this.game.StartGame();
            //if(leadPlayerId==3)
                //Toast.makeText(getActivity(),"Play the hand",Toast.LENGTH_SHORT).show();

        }




    }
}
