namespace OOPLR1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            MessageBox.Show("����������� �������� ����� ���");
        }

        private void button2_Click(object sender, EventArgs e)
        {
            label1.Text = "����� ���� ������.";
        }

        private void button3_Click(object sender, EventArgs e)
        {
            MessageBox.Show("�� �� ��������", "�� ������'�", MessageBoxButtons.YesNo);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            MessageBox.Show("���� GUI ����������, � ����� � ��������:\n� Java Swing\n� JavaFX\n� wxWidgets\n� Qt (QML)");
        }

        private void button5_Click(object sender, EventArgs e)
        {
            this.Text = "����� ��������� ����";
        }

        private void button6_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}