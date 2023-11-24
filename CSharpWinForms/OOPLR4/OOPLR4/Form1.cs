using Microsoft.VisualBasic;
using OOPLR4;
using System.Drawing;
using System.Windows.Forms;

namespace OOPLR2
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            new Form2().Show();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            new Newspaper();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            int inIssue, inPeriod, inPages, inWPP; string inDate, inTitle, inHeadline, inAuthor;
            while (!int.TryParse(Interaction.InputBox("������ ������", "��������"), out inIssue)) ;
            while (!int.TryParse(Interaction.InputBox("������ ����������� (� ����)", "��������"), out inPeriod)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� �������", "��������"), out inPages)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� ��� �� �������", "��������"), out inWPP)) ;
            inDate = Interaction.InputBox("������ ����", "��������");
            inTitle = Interaction.InputBox("������ �����", "��������");
            inHeadline = Interaction.InputBox("������ ���������", "��������");
            inAuthor = Interaction.InputBox("������ ������", "��������");
            new Newspaper(inIssue, inPeriod, inPages, inWPP, inDate, inTitle, inHeadline, inAuthor).Info();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            Magazine mag = new Magazine();
            Newspaper news = new Newspaper();
            PeriodicalPublication pub;
            pub = mag;
            pub.Info();
            pub = news;
            pub.Info();
        }

        private void button5_Click(object sender, EventArgs e)
        {
            new Magazine();
        }

        private void button6_Click(object sender, EventArgs e)
        {
            int inIssue, inPeriod, inPages, inWPP, inImgs, inAds; string inTitle, inAuthor;
            while (!int.TryParse(Interaction.InputBox("������ ������", "��������"), out inIssue)) ;
            while (!int.TryParse(Interaction.InputBox("������ ����������� (� ����)", "��������"), out inPeriod)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� �������", "��������"), out inPages)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� ��� �� �������", "��������"), out inWPP)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� ���������", "��������"), out inImgs)) ;
            while (!int.TryParse(Interaction.InputBox("������ ������� ������", "��������"), out inAds)) ;
            inTitle = Interaction.InputBox("������ �����", "��������");
            inAuthor = Interaction.InputBox("������ ������", "��������");
            new Magazine(inIssue, inPeriod, inPages, inWPP, inImgs, inAds, inTitle, inAuthor).Info();
        }

        private void button7_Click(object sender, EventArgs e)
        {
            MessageBox.Show($"�� ����� ������ ���� {PeriodicalPublication.Count} ���������� ������.");
        }

        private void OnNewButtonCreate(object sender, EventArgs e)
        {
            Random rand = new Random();
            Button newBtn = new Button();
            newBtn.Parent = this;
            newBtn.Size = new Size(188, 82);
            newBtn.Location = new Point(
                rand.Next(this.Width - 82),
                rand.Next(this.Height - 188)
            );
            newBtn.Text = "�������� ���� ������";
            newBtn.Click += new EventHandler(this.OnNewButtonCreate);
            newBtn.Visible = true;
            newBtn.BringToFront();
        }

        private void OnMoveLeftAllControls(object sender, EventArgs e)
        {
            foreach (Control control in this.Controls)
                control.Location = new Point(control.Location.X - 20, control.Location.Y);
        }

        private void button10_Click(object sender, EventArgs e)
        {
            Color[] colors = new Color[] { Color.Crimson, Color.LimeGreen, Color.Navy, Color.OrangeRed, Color.Wheat };
            Cursor[] cursors = new Cursor[] { Cursors.Arrow, Cursors.Hand, Cursors.WaitCursor, Cursors.VSplit, Cursors.Cross };
            Random rng = new Random();
            switch (rng.Next(3))
            {
                case 0:
                    foreach (Control c in this.Controls)
                        c.Location = new Point(c.Location.X, c.Location.Y + 5); break;
                case 1:
                    foreach (Control c in this.Controls)
                        c.BackColor = colors[rng.Next(colors.Length)]; break;
                case 2:
                    foreach (Control c in this.Controls)
                        c.Cursor = cursors[rng.Next(cursors.Length)]; break;
            }
        }

        private void button11_Click(object sender, EventArgs e)
        {
            PeriodicalPublication pub = new Magazine();
            Magazine mag = (Magazine)pub;
            string info = $"���� �� ���������� �������: {mag.RelativeTimeTillIssueNumber(2)}\n";
            info += $"������ ������ ��������� {mag.WordCount()} ���.\n";
            info += $"ʳ������ ���������: {mag.NofImages}.";
            MessageBox.Show(info, "������");
        }
    }

    public abstract class PeriodicalPublication
    {
        public static int Count = 0;
        public int Nomer;

        protected int Issue, PeriodInDays = 7, NumberOfPages, AvgWordsPerPage;
        protected string Title, Author = "��� ������";

        public PeriodicalPublication(int issue, int periodInDays, int numberOfPages, int avgWordsPerPage, string title, string author)
        {
            Issue = Math.Abs(issue);
            PeriodInDays = Math.Abs(periodInDays);
            AvgWordsPerPage = Math.Abs(avgWordsPerPage);
            NumberOfPages = Math.Abs(numberOfPages);
            Title = title;
            Author = author;
            Nomer = ++Count;
        }
        public PeriodicalPublication(int issue, int numberOfPages, string title)
        {
            Issue = Math.Abs(issue);
            NumberOfPages = Math.Abs(numberOfPages);
            Title = title;
            Nomer = ++Count;
            Info();
        }
        public PeriodicalPublication()
        {
            Issue = 1;
            NumberOfPages = 25;
            Title = "�������� �������";
            Nomer = ++Count;
            Info();
        }

        ~PeriodicalPublication()
        {
            MessageBox.Show($"��������� ��������� ������� �{Nomer}.");
            --Count;
            Info();
        }

        public abstract int RelativeTimeTillIssueNumber(int nextIssue);
        public abstract int WordCount();

        public virtual void Info()
        {
            string info = $"�{Nomer}: {Author} {Title}. ������ �{Issue}. {NumberOfPages} c.\n�����������: ";
            info += PeriodToVerbal();
            info += $"\n��������� ��� �� ����� �������: {AvgWordsPerPage}";
            MessageBox.Show(info, Title);
        }

        protected string PeriodToVerbal() => PeriodInDays switch
        {
            1 => "�������",
            2 => "����������",
            7 => "���������",
            14 => "������������",
            >= 28 and < 32 => "��������",
            365 | 366 => "������",
            _ => (
                PeriodInDays % 10 == 1
                    ? $"����� {PeriodInDays} ����"
                    : PeriodInDays % 10 >= 2 && PeriodInDays % 10 < 5
                        ? $"���� {PeriodInDays} ��"
                        : $"���� {PeriodInDays} ���"
            )
        };
    }

    public class Newspaper : PeriodicalPublication
    {
        string Headline, Date;

        public Newspaper(int issue, int periodInDays, int numberOfPages, int avgWordsPerPage, string date, string title, string headline, string author)
            : base(issue, periodInDays, numberOfPages, avgWordsPerPage, title, author)
        {
            Date = date;
            Headline = headline;
        }
        public Newspaper() : base()
        {
            Date = "01.01.1970";
            Title = "�������� ������";
            Headline = "����� �����";
        }

        public override int RelativeTimeTillIssueNumber(int nextIssue)
        {
            return Math.Abs((nextIssue - Issue) * PeriodInDays);
        }
        public override int WordCount()
        {
            return NumberOfPages * AvgWordsPerPage;
        }

        public override void Info()
        {
            string info = $"�{Nomer}: {Title} - {Headline}. ������ �{Issue} ({Date}). {NumberOfPages} c.\n�����������: ";
            info += PeriodToVerbal();
            info += $"\n��������� ��� �� ����� �������: {AvgWordsPerPage}";
            MessageBox.Show(info, Headline);
        }
    }

    public class Magazine : PeriodicalPublication
    {
        public int NofImages = 46, NofAds = 8;

        public Magazine(int issue, int periodInDays, int numberOfPages, int avgWordsPerPage, int nOfImages, int nOfAds, string title, string author)
            : base(issue, periodInDays, numberOfPages, avgWordsPerPage, title, author)
        {
            NofImages = nOfImages;
            NofAds = nOfAds;
        }
        public Magazine() : base()
        {
            Title = "��������� ������";
        }

        public override int RelativeTimeTillIssueNumber(int nextIssue)
        {
            return Math.Abs((nextIssue - Issue) * PeriodInDays);
        }
        public override int WordCount()
        {
            return NumberOfPages * AvgWordsPerPage;
        }

        public override void Info()
        {
            string info = $"�{Nomer}: {Title} : ������. ������ �{Issue}. {NumberOfPages} c.\n�����������: ";
            info += PeriodToVerbal();
            info += $"\n��������� ��� �� ����� �������: {AvgWordsPerPage}";
            info += $"\nʳ������ ���������: {NofImages}\nʳ������ ������: {NofAds}";
            MessageBox.Show(info, Title);
        }
    }
}