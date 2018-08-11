/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.CuisineBeans;

/**
 *
 * @author Alessandro Aloisio
 */
class ApplicationCuisineBeans implements TimeComputingBean {
    
    public static void main(String args[])
    {
        GetRecipeBean recipe = new GetRecipeBean("CC", 3);
        
    }
    
    public ApplicationCuisineBeans(String nom, int quantite)
    {
        GetRecipeBean recipe = new GetRecipeBean(nom, quantite);
        recipe.addRecipeListener(this);
        recipe.init();
        recipe.run();
    }
    
    public void ingredientsReceived(IngredientsEvent e)
    {
        System.out.println("+++++++++ !!!!! "+ e.toString() + "!!!!");
    }
    
}
